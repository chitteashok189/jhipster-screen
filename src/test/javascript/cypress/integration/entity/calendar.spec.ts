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

describe('Calendar e2e test', () => {
  const calendarPageUrl = '/calendar';
  const calendarPageUrlPattern = new RegExp('/calendar(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const calendarSample = {};

  let calendar: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/calendars+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/calendars').as('postEntityRequest');
    cy.intercept('DELETE', '/api/calendars/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (calendar) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/calendars/${calendar.id}`,
      }).then(() => {
        calendar = undefined;
      });
    }
  });

  it('Calendars menu should load Calendars page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('calendar');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Calendar').should('exist');
    cy.url().should('match', calendarPageUrlPattern);
  });

  describe('Calendar page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(calendarPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Calendar page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/calendar/new$'));
        cy.getEntityCreateUpdateHeading('Calendar');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', calendarPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/calendars',
          body: calendarSample,
        }).then(({ body }) => {
          calendar = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/calendars+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [calendar],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(calendarPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Calendar page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('calendar');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', calendarPageUrlPattern);
      });

      it('edit button click should load edit Calendar page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Calendar');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', calendarPageUrlPattern);
      });

      it('last delete button click should delete instance of Calendar', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('calendar').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', calendarPageUrlPattern);

        calendar = undefined;
      });
    });
  });

  describe('new Calendar page', () => {
    beforeEach(() => {
      cy.visit(`${calendarPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Calendar');
    });

    it('should create an instance of Calendar', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('7ac83933-9ecc-4b62-9eb8-84394513586a')
        .invoke('val')
        .should('match', new RegExp('7ac83933-9ecc-4b62-9eb8-84394513586a'));

      cy.get(`[data-cy="calenderDate"]`).type('2022-08-02T17:12').should('have.value', '2022-08-02T17:12');

      cy.get(`[data-cy="calenderYear"]`).type('89992').should('have.value', '89992');

      cy.get(`[data-cy="dayOfWeek"]`).type('11210').should('have.value', '11210');

      cy.get(`[data-cy="monthOfYear"]`).type('46809').should('have.value', '46809');

      cy.get(`[data-cy="weekOfMonth"]`).type('26450').should('have.value', '26450');

      cy.get(`[data-cy="weekOfQuarter"]`).type('76466').should('have.value', '76466');

      cy.get(`[data-cy="weekOfYear"]`).type('60545').should('have.value', '60545');

      cy.get(`[data-cy="dayOfQuarter"]`).type('53917').should('have.value', '53917');

      cy.get(`[data-cy="dayOfYear"]`).type('5457').should('have.value', '5457');

      cy.get(`[data-cy="createdBy"]`).type('786').should('have.value', '786');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T06:12').should('have.value', '2022-08-03T06:12');

      cy.get(`[data-cy="updatedBy"]`).type('36565').should('have.value', '36565');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T18:15').should('have.value', '2022-08-02T18:15');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        calendar = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', calendarPageUrlPattern);
    });
  });
});
