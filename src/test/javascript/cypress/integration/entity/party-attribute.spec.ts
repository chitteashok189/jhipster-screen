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

describe('PartyAttribute e2e test', () => {
  const partyAttributePageUrl = '/party-attribute';
  const partyAttributePageUrlPattern = new RegExp('/party-attribute(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyAttributeSample = {};

  let partyAttribute: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-attributes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-attributes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-attributes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyAttribute) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-attributes/${partyAttribute.id}`,
      }).then(() => {
        partyAttribute = undefined;
      });
    }
  });

  it('PartyAttributes menu should load PartyAttributes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-attribute');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyAttribute').should('exist');
    cy.url().should('match', partyAttributePageUrlPattern);
  });

  describe('PartyAttribute page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyAttributePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyAttribute page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-attribute/new$'));
        cy.getEntityCreateUpdateHeading('PartyAttribute');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyAttributePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-attributes',
          body: partyAttributeSample,
        }).then(({ body }) => {
          partyAttribute = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-attributes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyAttribute],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyAttributePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyAttribute page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyAttribute');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyAttributePageUrlPattern);
      });

      it('edit button click should load edit PartyAttribute page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyAttribute');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyAttributePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyAttribute', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyAttribute').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyAttributePageUrlPattern);

        partyAttribute = undefined;
      });
    });
  });

  describe('new PartyAttribute page', () => {
    beforeEach(() => {
      cy.visit(`${partyAttributePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyAttribute');
    });

    it('should create an instance of PartyAttribute', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('0aa9beeb-4f39-433f-ae92-daf6f232e5d2')
        .invoke('val')
        .should('match', new RegExp('0aa9beeb-4f39-433f-ae92-daf6f232e5d2'));

      cy.get(`[data-cy="attributeName"]`).type('Card e-markets').should('have.value', 'Card e-markets');

      cy.get(`[data-cy="attributeValue"]`).type('10661').should('have.value', '10661');

      cy.get(`[data-cy="createdBy"]`).type('43550').should('have.value', '43550');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T01:29').should('have.value', '2022-08-03T01:29');

      cy.get(`[data-cy="updatedBy"]`).type('43522').should('have.value', '43522');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T14:59').should('have.value', '2022-08-02T14:59');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyAttribute = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyAttributePageUrlPattern);
    });
  });
});
