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

describe('DeviceModel e2e test', () => {
  const deviceModelPageUrl = '/device-model';
  const deviceModelPageUrlPattern = new RegExp('/device-model(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const deviceModelSample = {};

  let deviceModel: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/device-models+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/device-models').as('postEntityRequest');
    cy.intercept('DELETE', '/api/device-models/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (deviceModel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/device-models/${deviceModel.id}`,
      }).then(() => {
        deviceModel = undefined;
      });
    }
  });

  it('DeviceModels menu should load DeviceModels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('device-model');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DeviceModel').should('exist');
    cy.url().should('match', deviceModelPageUrlPattern);
  });

  describe('DeviceModel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(deviceModelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DeviceModel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/device-model/new$'));
        cy.getEntityCreateUpdateHeading('DeviceModel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceModelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/device-models',
          body: deviceModelSample,
        }).then(({ body }) => {
          deviceModel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/device-models+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [deviceModel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(deviceModelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DeviceModel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('deviceModel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceModelPageUrlPattern);
      });

      it('edit button click should load edit DeviceModel page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DeviceModel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceModelPageUrlPattern);
      });

      it('last delete button click should delete instance of DeviceModel', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('deviceModel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceModelPageUrlPattern);

        deviceModel = undefined;
      });
    });
  });

  describe('new DeviceModel page', () => {
    beforeEach(() => {
      cy.visit(`${deviceModelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DeviceModel');
    });

    it('should create an instance of DeviceModel', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('6b0405e8-7166-4b07-be1e-7fa26a380d3c')
        .invoke('val')
        .should('match', new RegExp('6b0405e8-7166-4b07-be1e-7fa26a380d3c'));

      cy.get(`[data-cy="modelName"]`).type('access Groves').should('have.value', 'access Groves');

      cy.get(`[data-cy="type"]`).type('88937').should('have.value', '88937');

      cy.get(`[data-cy="manufacturer"]`).type('methodologies').should('have.value', 'methodologies');

      cy.get(`[data-cy="productCode"]`).type('85895').should('have.value', '85895');

      cy.get(`[data-cy="properties"]`).type('96394').should('have.value', '96394');

      cy.get(`[data-cy="description"]`).type('salmon programming').should('have.value', 'salmon programming');

      cy.get(`[data-cy="createdBy"]`).type('49466').should('have.value', '49466');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T02:32').should('have.value', '2022-08-03T02:32');

      cy.get(`[data-cy="updatedBy"]`).type('74866').should('have.value', '74866');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T11:24').should('have.value', '2022-08-02T11:24');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        deviceModel = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', deviceModelPageUrlPattern);
    });
  });
});
