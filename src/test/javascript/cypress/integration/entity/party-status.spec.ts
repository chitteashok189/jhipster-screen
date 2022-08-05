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

describe('PartyStatus e2e test', () => {
  const partyStatusPageUrl = '/party-status';
  const partyStatusPageUrlPattern = new RegExp('/party-status(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyStatusSample = {};

  let partyStatus: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-statuses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-statuses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-statuses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyStatus) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-statuses/${partyStatus.id}`,
      }).then(() => {
        partyStatus = undefined;
      });
    }
  });

  it('PartyStatuses menu should load PartyStatuses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-status');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyStatus').should('exist');
    cy.url().should('match', partyStatusPageUrlPattern);
  });

  describe('PartyStatus page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyStatusPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyStatus page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-status/new$'));
        cy.getEntityCreateUpdateHeading('PartyStatus');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-statuses',
          body: partyStatusSample,
        }).then(({ body }) => {
          partyStatus = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-statuses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyStatus],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyStatusPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyStatus page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyStatus');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusPageUrlPattern);
      });

      it('edit button click should load edit PartyStatus page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyStatus');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusPageUrlPattern);
      });

      it('last delete button click should delete instance of PartyStatus', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyStatus').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusPageUrlPattern);

        partyStatus = undefined;
      });
    });
  });

  describe('new PartyStatus page', () => {
    beforeEach(() => {
      cy.visit(`${partyStatusPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyStatus');
    });

    it('should create an instance of PartyStatus', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('0294393f-e79d-40ce-a8f5-d1eeffd46025')
        .invoke('val')
        .should('match', new RegExp('0294393f-e79d-40ce-a8f5-d1eeffd46025'));

      cy.get(`[data-cy="statusDate"]`).type('2022-08-02T22:46').should('have.value', '2022-08-02T22:46');

      cy.get(`[data-cy="createdBy"]`).type('41684').should('have.value', '41684');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T07:33').should('have.value', '2022-08-03T07:33');

      cy.get(`[data-cy="updatedBy"]`).type('85423').should('have.value', '85423');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T14:11').should('have.value', '2022-08-02T14:11');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyStatus = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyStatusPageUrlPattern);
    });
  });
});
