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

describe('PartyTypeAttribute e2e test', () => {
  const partyTypeAttributePageUrl = '/party-type-attribute';
  const partyTypeAttributePageUrlPattern = new RegExp('/party-type-attribute(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyTypeAttributeSample = {};

  let partyTypeAttribute: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-type-attributes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-type-attributes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-type-attributes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyTypeAttribute) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-type-attributes/${partyTypeAttribute.id}`,
      }).then(() => {
        partyTypeAttribute = undefined;
      });
    }
  });

  it('PartyTypeAttributes menu should load PartyTypeAttributes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-type-attribute');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyTypeAttribute').should('exist');
    cy.url().should('match', partyTypeAttributePageUrlPattern);
  });

  describe('PartyTypeAttribute page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyTypeAttributePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyTypeAttribute page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-type-attribute/new$'));
        cy.getEntityCreateUpdateHeading('PartyTypeAttribute');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypeAttributePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-type-attributes',
          body: partyTypeAttributeSample,
        }).then(({ body }) => {
          partyTypeAttribute = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-type-attributes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyTypeAttribute],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyTypeAttributePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyTypeAttribute page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyTypeAttribute');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypeAttributePageUrlPattern);
      });

      it('edit button click should load edit PartyTypeAttribute page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyTypeAttribute');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypeAttributePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyTypeAttribute', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyTypeAttribute').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypeAttributePageUrlPattern);

        partyTypeAttribute = undefined;
      });
    });
  });

  describe('new PartyTypeAttribute page', () => {
    beforeEach(() => {
      cy.visit(`${partyTypeAttributePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyTypeAttribute');
    });

    it('should create an instance of PartyTypeAttribute', () => {
      cy.get(`[data-cy="gUID"]`).type('69484').should('have.value', '69484');

      cy.get(`[data-cy="attributeName"]`).type('Wooden transparent').should('have.value', 'Wooden transparent');

      cy.get(`[data-cy="createdBy"]`).type('96396').should('have.value', '96396');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T06:24').should('have.value', '2022-08-03T06:24');

      cy.get(`[data-cy="updatedBy"]`).type('88145').should('have.value', '88145');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T11:53').should('have.value', '2022-08-02T11:53');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyTypeAttribute = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyTypeAttributePageUrlPattern);
    });
  });
});
