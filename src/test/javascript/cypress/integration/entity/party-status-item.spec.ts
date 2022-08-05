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

describe('PartyStatusItem e2e test', () => {
  const partyStatusItemPageUrl = '/party-status-item';
  const partyStatusItemPageUrlPattern = new RegExp('/party-status-item(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyStatusItemSample = {};

  let partyStatusItem: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-status-items+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-status-items').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-status-items/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyStatusItem) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-status-items/${partyStatusItem.id}`,
      }).then(() => {
        partyStatusItem = undefined;
      });
    }
  });

  it('PartyStatusItems menu should load PartyStatusItems page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-status-item');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyStatusItem').should('exist');
    cy.url().should('match', partyStatusItemPageUrlPattern);
  });

  describe('PartyStatusItem page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyStatusItemPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyStatusItem page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-status-item/new$'));
        cy.getEntityCreateUpdateHeading('PartyStatusItem');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusItemPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-status-items',
          body: partyStatusItemSample,
        }).then(({ body }) => {
          partyStatusItem = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-status-items+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyStatusItem],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyStatusItemPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyStatusItem page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyStatusItem');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusItemPageUrlPattern);
      });

      it('edit button click should load edit PartyStatusItem page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyStatusItem');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusItemPageUrlPattern);
      });

      it('last delete button click should delete instance of PartyStatusItem', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyStatusItem').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusItemPageUrlPattern);

        partyStatusItem = undefined;
      });
    });
  });

  describe('new PartyStatusItem page', () => {
    beforeEach(() => {
      cy.visit(`${partyStatusItemPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyStatusItem');
    });

    it('should create an instance of PartyStatusItem', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('c60b7736-337a-4db3-a8ae-26598c06fb19')
        .invoke('val')
        .should('match', new RegExp('c60b7736-337a-4db3-a8ae-26598c06fb19'));

      cy.get(`[data-cy="statusCode"]`).type('65712').should('have.value', '65712');

      cy.get(`[data-cy="sequenceID"]`).type('17287').should('have.value', '17287');

      cy.get(`[data-cy="description"]`).type('deposit invoice').should('have.value', 'deposit invoice');

      cy.get(`[data-cy="createdBy"]`).type('38643').should('have.value', '38643');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T03:07').should('have.value', '2022-08-03T03:07');

      cy.get(`[data-cy="updatedBy"]`).type('30398').should('have.value', '30398');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T11:12').should('have.value', '2022-08-02T11:12');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyStatusItem = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyStatusItemPageUrlPattern);
    });
  });
});
