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

describe('PartyClassification e2e test', () => {
  const partyClassificationPageUrl = '/party-classification';
  const partyClassificationPageUrlPattern = new RegExp('/party-classification(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyClassificationSample = {};

  let partyClassification: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-classifications+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-classifications').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-classifications/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyClassification) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-classifications/${partyClassification.id}`,
      }).then(() => {
        partyClassification = undefined;
      });
    }
  });

  it('PartyClassifications menu should load PartyClassifications page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-classification');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyClassification').should('exist');
    cy.url().should('match', partyClassificationPageUrlPattern);
  });

  describe('PartyClassification page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyClassificationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyClassification page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-classification/new$'));
        cy.getEntityCreateUpdateHeading('PartyClassification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyClassificationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-classifications',
          body: partyClassificationSample,
        }).then(({ body }) => {
          partyClassification = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-classifications+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyClassification],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyClassificationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyClassification page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyClassification');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyClassificationPageUrlPattern);
      });

      it('edit button click should load edit PartyClassification page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyClassification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyClassificationPageUrlPattern);
      });

      it('last delete button click should delete instance of PartyClassification', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyClassification').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyClassificationPageUrlPattern);

        partyClassification = undefined;
      });
    });
  });

  describe('new PartyClassification page', () => {
    beforeEach(() => {
      cy.visit(`${partyClassificationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyClassification');
    });

    it('should create an instance of PartyClassification', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('09c77184-6b89-4db0-92fe-bd74b033fe8c')
        .invoke('val')
        .should('match', new RegExp('09c77184-6b89-4db0-92fe-bd74b033fe8c'));

      cy.get(`[data-cy="fromDate"]`).type('2022-08-02T19:13').should('have.value', '2022-08-02T19:13');

      cy.get(`[data-cy="thruDate"]`).type('2022-08-03T04:51').should('have.value', '2022-08-03T04:51');

      cy.get(`[data-cy="createdBy"]`).type('85019').should('have.value', '85019');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T20:39').should('have.value', '2022-08-02T20:39');

      cy.get(`[data-cy="updatedBy"]`).type('73868').should('have.value', '73868');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T13:01').should('have.value', '2022-08-02T13:01');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyClassification = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyClassificationPageUrlPattern);
    });
  });
});
