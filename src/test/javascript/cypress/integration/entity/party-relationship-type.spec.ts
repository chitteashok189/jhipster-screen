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

describe('PartyRelationshipType e2e test', () => {
  const partyRelationshipTypePageUrl = '/party-relationship-type';
  const partyRelationshipTypePageUrlPattern = new RegExp('/party-relationship-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyRelationshipTypeSample = {};

  let partyRelationshipType: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-relationship-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-relationship-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-relationship-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyRelationshipType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-relationship-types/${partyRelationshipType.id}`,
      }).then(() => {
        partyRelationshipType = undefined;
      });
    }
  });

  it('PartyRelationshipTypes menu should load PartyRelationshipTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-relationship-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyRelationshipType').should('exist');
    cy.url().should('match', partyRelationshipTypePageUrlPattern);
  });

  describe('PartyRelationshipType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyRelationshipTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyRelationshipType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-relationship-type/new$'));
        cy.getEntityCreateUpdateHeading('PartyRelationshipType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-relationship-types',
          body: partyRelationshipTypeSample,
        }).then(({ body }) => {
          partyRelationshipType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-relationship-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyRelationshipType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyRelationshipTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyRelationshipType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyRelationshipType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipTypePageUrlPattern);
      });

      it('edit button click should load edit PartyRelationshipType page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyRelationshipType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipTypePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyRelationshipType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyRelationshipType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipTypePageUrlPattern);

        partyRelationshipType = undefined;
      });
    });
  });

  describe('new PartyRelationshipType page', () => {
    beforeEach(() => {
      cy.visit(`${partyRelationshipTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyRelationshipType');
    });

    it('should create an instance of PartyRelationshipType', () => {
      cy.get(`[data-cy="gUID"]`).type('92983').should('have.value', '92983');

      cy.get(`[data-cy="hasTable"]`).should('not.be.checked');
      cy.get(`[data-cy="hasTable"]`).click().should('be.checked');

      cy.get(`[data-cy="partyRelationshipName"]`).type('indigo Account synthesize').should('have.value', 'indigo Account synthesize');

      cy.get(`[data-cy="description"]`).type('haptic People&#39;s').should('have.value', 'haptic People&#39;s');

      cy.get(`[data-cy="roleTypeIdValidFrom"]`).type('16293').should('have.value', '16293');

      cy.get(`[data-cy="roleTypeIdValidTo"]`).type('351').should('have.value', '351');

      cy.get(`[data-cy="createdBy"]`).type('59140').should('have.value', '59140');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T06:49').should('have.value', '2022-08-03T06:49');

      cy.get(`[data-cy="updatedBy"]`).type('60678').should('have.value', '60678');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T03:23').should('have.value', '2022-08-03T03:23');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyRelationshipType = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyRelationshipTypePageUrlPattern);
    });
  });
});
