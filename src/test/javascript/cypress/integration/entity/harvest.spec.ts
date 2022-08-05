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

describe('Harvest e2e test', () => {
  const harvestPageUrl = '/harvest';
  const harvestPageUrlPattern = new RegExp('/harvest(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const harvestSample = {};

  let harvest: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/harvests+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/harvests').as('postEntityRequest');
    cy.intercept('DELETE', '/api/harvests/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (harvest) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/harvests/${harvest.id}`,
      }).then(() => {
        harvest = undefined;
      });
    }
  });

  it('Harvests menu should load Harvests page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('harvest');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Harvest').should('exist');
    cy.url().should('match', harvestPageUrlPattern);
  });

  describe('Harvest page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(harvestPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Harvest page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/harvest/new$'));
        cy.getEntityCreateUpdateHeading('Harvest');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', harvestPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/harvests',
          body: harvestSample,
        }).then(({ body }) => {
          harvest = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/harvests+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [harvest],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(harvestPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Harvest page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('harvest');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', harvestPageUrlPattern);
      });

      it('edit button click should load edit Harvest page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Harvest');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', harvestPageUrlPattern);
      });

      it('last delete button click should delete instance of Harvest', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('harvest').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', harvestPageUrlPattern);

        harvest = undefined;
      });
    });
  });

  describe('new Harvest page', () => {
    beforeEach(() => {
      cy.visit(`${harvestPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Harvest');
    });

    it('should create an instance of Harvest', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('3de96d97-ed19-4a1b-a6e8-a3bec290bfce')
        .invoke('val')
        .should('match', new RegExp('3de96d97-ed19-4a1b-a6e8-a3bec290bfce'));

      cy.get(`[data-cy="harvestingDate"]`).type('2022-08-03T08:24').should('have.value', '2022-08-03T08:24');

      cy.get(`[data-cy="createdBy"]`).type('62288').should('have.value', '62288');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T23:07').should('have.value', '2022-08-02T23:07');

      cy.get(`[data-cy="updatedBy"]`).type('80376').should('have.value', '80376');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T19:15').should('have.value', '2022-08-02T19:15');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        harvest = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', harvestPageUrlPattern);
    });
  });
});
