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

describe('PlantFactoryController e2e test', () => {
  const plantFactoryControllerPageUrl = '/plant-factory-controller';
  const plantFactoryControllerPageUrlPattern = new RegExp('/plant-factory-controller(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const plantFactoryControllerSample = {};

  let plantFactoryController: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/plant-factory-controllers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/plant-factory-controllers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/plant-factory-controllers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (plantFactoryController) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/plant-factory-controllers/${plantFactoryController.id}`,
      }).then(() => {
        plantFactoryController = undefined;
      });
    }
  });

  it('PlantFactoryControllers menu should load PlantFactoryControllers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('plant-factory-controller');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PlantFactoryController').should('exist');
    cy.url().should('match', plantFactoryControllerPageUrlPattern);
  });

  describe('PlantFactoryController page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(plantFactoryControllerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PlantFactoryController page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/plant-factory-controller/new$'));
        cy.getEntityCreateUpdateHeading('PlantFactoryController');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryControllerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/plant-factory-controllers',
          body: plantFactoryControllerSample,
        }).then(({ body }) => {
          plantFactoryController = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/plant-factory-controllers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [plantFactoryController],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(plantFactoryControllerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PlantFactoryController page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('plantFactoryController');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryControllerPageUrlPattern);
      });

      it('edit button click should load edit PlantFactoryController page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PlantFactoryController');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryControllerPageUrlPattern);
      });

      it('last delete button click should delete instance of PlantFactoryController', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('plantFactoryController').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantFactoryControllerPageUrlPattern);

        plantFactoryController = undefined;
      });
    });
  });

  describe('new PlantFactoryController page', () => {
    beforeEach(() => {
      cy.visit(`${plantFactoryControllerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PlantFactoryController');
    });

    it('should create an instance of PlantFactoryController', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('7f9248c8-5cf1-40bc-8369-f15ee6ad1bd7')
        .invoke('val')
        .should('match', new RegExp('7f9248c8-5cf1-40bc-8369-f15ee6ad1bd7'));

      cy.get(`[data-cy="source"]`).select('Manual');

      cy.get(`[data-cy="airTemperature"]`).type('12596').should('have.value', '12596');

      cy.get(`[data-cy="relativeHumidity"]`).type('89551').should('have.value', '89551');

      cy.get(`[data-cy="vPD"]`).type('47593').should('have.value', '47593');

      cy.get(`[data-cy="evapotranspiration"]`).type('71957').should('have.value', '71957');

      cy.get(`[data-cy="barometricPressure"]`).type('15148').should('have.value', '15148');

      cy.get(`[data-cy="seaLevelPressure"]`).type('43743').should('have.value', '43743');

      cy.get(`[data-cy="co2Level"]`).type('75142').should('have.value', '75142');

      cy.get(`[data-cy="nutrientTankLevel"]`).type('34116').should('have.value', '34116');

      cy.get(`[data-cy="dewPoint"]`).type('4784').should('have.value', '4784');

      cy.get(`[data-cy="heatIndex"]`).type('20400').should('have.value', '20400');

      cy.get(`[data-cy="turbidity"]`).type('70209').should('have.value', '70209');

      cy.get(`[data-cy="createdBy"]`).type('49497').should('have.value', '49497');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T17:55').should('have.value', '2022-08-02T17:55');

      cy.get(`[data-cy="updatedBy"]`).type('92122').should('have.value', '92122');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T08:40').should('have.value', '2022-08-03T08:40');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        plantFactoryController = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', plantFactoryControllerPageUrlPattern);
    });
  });
});
