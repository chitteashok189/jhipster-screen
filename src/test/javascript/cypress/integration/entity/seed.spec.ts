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

describe('Seed e2e test', () => {
  const seedPageUrl = '/seed';
  const seedPageUrlPattern = new RegExp('/seed(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const seedSample = {};

  let seed: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/seeds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/seeds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/seeds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (seed) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/seeds/${seed.id}`,
      }).then(() => {
        seed = undefined;
      });
    }
  });

  it('Seeds menu should load Seeds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('seed');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Seed').should('exist');
    cy.url().should('match', seedPageUrlPattern);
  });

  describe('Seed page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(seedPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Seed page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/seed/new$'));
        cy.getEntityCreateUpdateHeading('Seed');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seedPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/seeds',
          body: seedSample,
        }).then(({ body }) => {
          seed = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/seeds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [seed],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(seedPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Seed page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('seed');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seedPageUrlPattern);
      });

      it('edit button click should load edit Seed page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Seed');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seedPageUrlPattern);
      });

      it('last delete button click should delete instance of Seed', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('seed').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seedPageUrlPattern);

        seed = undefined;
      });
    });
  });

  describe('new Seed page', () => {
    beforeEach(() => {
      cy.visit(`${seedPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Seed');
    });

    it('should create an instance of Seed', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('96a7f168-0d1d-4fad-bc98-3475f667ee10')
        .invoke('val')
        .should('match', new RegExp('96a7f168-0d1d-4fad-bc98-3475f667ee10'));

      cy.get(`[data-cy="breederID"]`).type('Cambridgeshire Books').should('have.value', 'Cambridgeshire Books');

      cy.get(`[data-cy="seedClass"]`).select('Registered');

      cy.get(`[data-cy="variety"]`).type('programming').should('have.value', 'programming');

      cy.get(`[data-cy="seedRate"]`).select('Kg_Ha');

      cy.get(`[data-cy="germinationPercentage"]`).type('60148').should('have.value', '60148');

      cy.get(`[data-cy="treatment"]`).select('UV');

      cy.get(`[data-cy="origin"]`).type('Awesome Tools').should('have.value', 'Awesome Tools');

      cy.get(`[data-cy="createdBy"]`).type('75083').should('have.value', '75083');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T21:15').should('have.value', '2022-08-02T21:15');

      cy.get(`[data-cy="updatedBy"]`).type('66378').should('have.value', '66378');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T04:27').should('have.value', '2022-08-03T04:27');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        seed = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', seedPageUrlPattern);
    });
  });
});
