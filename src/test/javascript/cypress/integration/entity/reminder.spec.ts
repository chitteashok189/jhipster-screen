import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Reminder e2e test', () => {
  const reminderPageUrl = '/reminder';
  const reminderPageUrlPattern = new RegExp('/reminder(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const reminderSample = {};

  let reminder: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/reminders+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/reminders').as('postEntityRequest');
    cy.intercept('DELETE', '/api/reminders/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (reminder) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/reminders/${reminder.id}`,
      }).then(() => {
        reminder = undefined;
      });
    }
  });

  it('Reminders menu should load Reminders page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('reminder');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Reminder').should('exist');
    cy.url().should('match', reminderPageUrlPattern);
  });

  describe('Reminder page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(reminderPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Reminder page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/reminder/new$'));
        cy.getEntityCreateUpdateHeading('Reminder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reminderPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/reminders',
          body: reminderSample,
        }).then(({ body }) => {
          reminder = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/reminders+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [reminder],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(reminderPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Reminder page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('reminder');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reminderPageUrlPattern);
      });

      it('edit button click should load edit Reminder page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Reminder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reminderPageUrlPattern);
      });

      it('last delete button click should delete instance of Reminder', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('reminder').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reminderPageUrlPattern);

        reminder = undefined;
      });
    });
  });

  describe('new Reminder page', () => {
    beforeEach(() => {
      cy.visit(`${reminderPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Reminder');
    });

    it('should create an instance of Reminder', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('20dcd2be-3ae4-4bf3-b65c-eff0ede2d8e9')
        .invoke('val')
        .should('match', new RegExp('20dcd2be-3ae4-4bf3-b65c-eff0ede2d8e9'));

      cy.get(`[data-cy="name"]`).type('bypass Account optical').should('have.value', 'bypass Account optical');

      cy.get(`[data-cy="date"]`).type('2022-08-03T08:15').should('have.value', '2022-08-03T08:15');

      cy.get(`[data-cy="time"]`).type('60113').should('have.value', '60113');

      cy.get(`[data-cy="item"]`).select('Doser');

      cy.get(`[data-cy="description"]`).type('44823').should('have.value', '44823');

      cy.get(`[data-cy="createdBy"]`).type('30189').should('have.value', '30189');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T19:09').should('have.value', '2022-08-02T19:09');

      cy.get(`[data-cy="updatedBy"]`).type('59963').should('have.value', '59963');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T02:11').should('have.value', '2022-08-03T02:11');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        reminder = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', reminderPageUrlPattern);
    });
  });
});
