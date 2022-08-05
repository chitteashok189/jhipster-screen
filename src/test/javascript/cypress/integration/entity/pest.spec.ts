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

describe('Pest e2e test', () => {
  const pestPageUrl = '/pest';
  const pestPageUrlPattern = new RegExp('/pest(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pestSample = {};

  let pest: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/pests+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pests').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pests/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pest) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pests/${pest.id}`,
      }).then(() => {
        pest = undefined;
      });
    }
  });

  it('Pests menu should load Pests page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pest');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Pest').should('exist');
    cy.url().should('match', pestPageUrlPattern);
  });

  describe('Pest page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pestPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Pest page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pest/new$'));
        cy.getEntityCreateUpdateHeading('Pest');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pests',
          body: pestSample,
        }).then(({ body }) => {
          pest = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pests+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [pest],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(pestPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Pest page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pest');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestPageUrlPattern);
      });

      it('edit button click should load edit Pest page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Pest');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestPageUrlPattern);
      });

      it('last delete button click should delete instance of Pest', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('pest').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestPageUrlPattern);

        pest = undefined;
      });
    });
  });

  describe('new Pest page', () => {
    beforeEach(() => {
      cy.visit(`${pestPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Pest');
    });

    it('should create an instance of Pest', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('7ee18a61-8fec-442e-98c0-fece1a235ebc')
        .invoke('val')
        .should('match', new RegExp('7ee18a61-8fec-442e-98c0-fece1a235ebc'));

      cy.get(`[data-cy="threatLevel"]`).select('Moderate');

      cy.get(`[data-cy="name"]`).type('application payment').should('have.value', 'application payment');

      cy.get(`[data-cy="scientificName"]`).type('Azerbaijan').should('have.value', 'Azerbaijan');

      cy.get(`[data-cy="attachements"]`).type('80541').should('have.value', '80541');

      cy.get(`[data-cy="createdBy"]`).type('1738').should('have.value', '1738');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T10:50').should('have.value', '2022-08-02T10:50');

      cy.get(`[data-cy="updatedBy"]`).type('18146').should('have.value', '18146');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T19:06').should('have.value', '2022-08-02T19:06');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        pest = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', pestPageUrlPattern);
    });
  });
});
