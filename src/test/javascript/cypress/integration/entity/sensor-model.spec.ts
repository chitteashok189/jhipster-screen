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

describe('SensorModel e2e test', () => {
  const sensorModelPageUrl = '/sensor-model';
  const sensorModelPageUrlPattern = new RegExp('/sensor-model(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sensorModelSample = {};

  let sensorModel: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sensor-models+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sensor-models').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sensor-models/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sensorModel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sensor-models/${sensorModel.id}`,
      }).then(() => {
        sensorModel = undefined;
      });
    }
  });

  it('SensorModels menu should load SensorModels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sensor-model');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SensorModel').should('exist');
    cy.url().should('match', sensorModelPageUrlPattern);
  });

  describe('SensorModel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sensorModelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SensorModel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sensor-model/new$'));
        cy.getEntityCreateUpdateHeading('SensorModel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorModelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sensor-models',
          body: sensorModelSample,
        }).then(({ body }) => {
          sensorModel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sensor-models+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [sensorModel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(sensorModelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SensorModel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sensorModel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorModelPageUrlPattern);
      });

      it('edit button click should load edit SensorModel page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SensorModel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorModelPageUrlPattern);
      });

      it('last delete button click should delete instance of SensorModel', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('sensorModel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorModelPageUrlPattern);

        sensorModel = undefined;
      });
    });
  });

  describe('new SensorModel page', () => {
    beforeEach(() => {
      cy.visit(`${sensorModelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SensorModel');
    });

    it('should create an instance of SensorModel', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('701bce27-94de-4890-988d-dc69381a9867')
        .invoke('val')
        .should('match', new RegExp('701bce27-94de-4890-988d-dc69381a9867'));

      cy.get(`[data-cy="sensorType"]`).type('52383').should('have.value', '52383');

      cy.get(`[data-cy="manufacturer"]`).type('matrix transmitting').should('have.value', 'matrix transmitting');

      cy.get(`[data-cy="productCode"]`).type('Optimization').should('have.value', 'Optimization');

      cy.get(`[data-cy="sensorMeasure"]`).type('73249').should('have.value', '73249');

      cy.get(`[data-cy="modelName"]`).type('Borders loyalty').should('have.value', 'Borders loyalty');

      cy.get(`[data-cy="properties"]`).type('Checking').should('have.value', 'Checking');

      cy.get(`[data-cy="description"]`).type('withdrawal bluetooth one-to-one').should('have.value', 'withdrawal bluetooth one-to-one');

      cy.get(`[data-cy="createdBy"]`).type('92745').should('have.value', '92745');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T10:51').should('have.value', '2022-08-02T10:51');

      cy.get(`[data-cy="updatedBy"]`).type('26897').should('have.value', '26897');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T23:49').should('have.value', '2022-08-02T23:49');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        sensorModel = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', sensorModelPageUrlPattern);
    });
  });
});
