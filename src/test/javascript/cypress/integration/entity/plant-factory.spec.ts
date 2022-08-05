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

describe('PlantFactory e2e test', () => {
  const plantFactoryPageUrl = '/plant-factory';
  const plantFactoryPageUrlPattern = new RegExp('/plant-factory(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const plantFactorySample = {};

  let plantFactory: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/plant-factories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/plant-factories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/plant-factories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (plantFactory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/plant-factories/${plantFactory.id}`,
      }).then(() => {
        plantFactory = undefined;
      });
    }
  });

  it('PlantFactories menu should load PlantFactories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('plant-factory');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PlantFactory').should('exist');
    cy.url().should('match', plantFactoryPageUrlPattern);
  });

  describe('PlantFactory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(plantFactoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PlantFactory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/plant-factory/new$'));
        cy.getEntityCreateUpdateHeading('PlantFactory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/plant-factories',
          body: plantFactorySample,
        }).then(({ body }) => {
          plantFactory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/plant-factories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [plantFactory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(plantFactoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PlantFactory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('plantFactory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryPageUrlPattern);
      });

      it('edit button click should load edit PlantFactory page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PlantFactory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryPageUrlPattern);
      });

      it('last delete button click should delete instance of PlantFactory', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('plantFactory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryPageUrlPattern);

        plantFactory = undefined;
      });
    });
  });

  describe('new PlantFactory page', () => {
    beforeEach(() => {
      cy.visit(`${plantFactoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PlantFactory');
    });

    it('should create an instance of PlantFactory', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('a361402e-5e34-4f2e-bf8f-38c6302bd400')
        .invoke('val')
        .should('match', new RegExp('a361402e-5e34-4f2e-bf8f-38c6302bd400'));

      cy.get(`[data-cy="plantFactoryName"]`)
        .type('transmitter Music Decentralized')
        .should('have.value', 'transmitter Music Decentralized');

      cy.get(`[data-cy="plantFactoryTypeID"]`).select('NVPH_Naturally');

      cy.get(`[data-cy="plantFactorySubType"]`).select('Giga');

      cy.get(`[data-cy="plantFactoryDescription"]`).type('yellow Graphical').should('have.value', 'yellow Graphical');

      cy.get(`[data-cy="createdBy"]`).type('86746').should('have.value', '86746');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T13:57').should('have.value', '2022-08-02T13:57');

      cy.get(`[data-cy="updatedBy"]`).type('34942').should('have.value', '34942');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T02:34').should('have.value', '2022-08-03T02:34');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        plantFactory = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', plantFactoryPageUrlPattern);
    });
  });
});
