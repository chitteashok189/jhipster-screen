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

describe('Sensor e2e test', () => {
  const sensorPageUrl = '/sensor';
  const sensorPageUrlPattern = new RegExp('/sensor(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sensorSample = {};

  let sensor: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sensors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sensors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sensors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sensor) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sensors/${sensor.id}`,
      }).then(() => {
        sensor = undefined;
      });
    }
  });

  it('Sensors menu should load Sensors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sensor');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Sensor').should('exist');
    cy.url().should('match', sensorPageUrlPattern);
  });

  describe('Sensor page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sensorPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Sensor page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sensor/new$'));
        cy.getEntityCreateUpdateHeading('Sensor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sensors',
          body: sensorSample,
        }).then(({ body }) => {
          sensor = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sensors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [sensor],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(sensorPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Sensor page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sensor');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorPageUrlPattern);
      });

      it('edit button click should load edit Sensor page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sensor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorPageUrlPattern);
      });

      it('last delete button click should delete instance of Sensor', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('sensor').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sensorPageUrlPattern);

        sensor = undefined;
      });
    });
  });

  describe('new Sensor page', () => {
    beforeEach(() => {
      cy.visit(`${sensorPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Sensor');
    });

    it('should create an instance of Sensor', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('9960fd3d-148d-44b5-826a-0ab5b5460fb1')
        .invoke('val')
        .should('match', new RegExp('9960fd3d-148d-44b5-826a-0ab5b5460fb1'));

      cy.get(`[data-cy="sensorName"]`).type('Generic').should('have.value', 'Generic');

      cy.get(`[data-cy="sensorModelName"]`).type('Square Realigned').should('have.value', 'Square Realigned');

      cy.get(`[data-cy="hardwareID"]`).type('66304').should('have.value', '66304');

      cy.get(`[data-cy="port"]`).type('41525').should('have.value', '41525');

      cy.get(`[data-cy="properties"]`).type('Steel Gorgeous').should('have.value', 'Steel Gorgeous');

      cy.get(`[data-cy="description"]`).type('Architect').should('have.value', 'Architect');

      cy.get(`[data-cy="createdBy"]`).type('19611').should('have.value', '19611');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T02:57').should('have.value', '2022-08-03T02:57');

      cy.get(`[data-cy="updatedBy"]`).type('48277').should('have.value', '48277');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T08:13').should('have.value', '2022-08-03T08:13');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        sensor = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', sensorPageUrlPattern);
    });
  });
});
