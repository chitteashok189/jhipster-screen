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

describe('DeviceLevel e2e test', () => {
  const deviceLevelPageUrl = '/device-level';
  const deviceLevelPageUrlPattern = new RegExp('/device-level(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const deviceLevelSample = {};

  let deviceLevel: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/device-levels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/device-levels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/device-levels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (deviceLevel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/device-levels/${deviceLevel.id}`,
      }).then(() => {
        deviceLevel = undefined;
      });
    }
  });

  it('DeviceLevels menu should load DeviceLevels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('device-level');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DeviceLevel').should('exist');
    cy.url().should('match', deviceLevelPageUrlPattern);
  });

  describe('DeviceLevel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(deviceLevelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DeviceLevel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/device-level/new$'));
        cy.getEntityCreateUpdateHeading('DeviceLevel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceLevelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/device-levels',
          body: deviceLevelSample,
        }).then(({ body }) => {
          deviceLevel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/device-levels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [deviceLevel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(deviceLevelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DeviceLevel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('deviceLevel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceLevelPageUrlPattern);
      });

      it('edit button click should load edit DeviceLevel page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DeviceLevel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceLevelPageUrlPattern);
      });

      it('last delete button click should delete instance of DeviceLevel', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('deviceLevel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', deviceLevelPageUrlPattern);

        deviceLevel = undefined;
      });
    });
  });

  describe('new DeviceLevel page', () => {
    beforeEach(() => {
      cy.visit(`${deviceLevelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DeviceLevel');
    });

    it('should create an instance of DeviceLevel', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('f9b73f46-4d51-4692-adb2-098a606626e4')
        .invoke('val')
        .should('match', new RegExp('f9b73f46-4d51-4692-adb2-098a606626e4'));

      cy.get(`[data-cy="levelName"]`).type('withdrawal').should('have.value', 'withdrawal');

      cy.get(`[data-cy="deviceLevelType"]`).type('84264').should('have.value', '84264');

      cy.get(`[data-cy="manufacturer"]`).type('middleware payment Strategist').should('have.value', 'middleware payment Strategist');

      cy.get(`[data-cy="productCode"]`).type('93671').should('have.value', '93671');

      cy.get(`[data-cy="ports"]`).type('33744').should('have.value', '33744');

      cy.get(`[data-cy="properties"]`).type('utilisation').should('have.value', 'utilisation');

      cy.get(`[data-cy="description"]`).type('Bacon leverage').should('have.value', 'Bacon leverage');

      cy.get(`[data-cy="createdBy"]`).type('64669').should('have.value', '64669');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T00:39').should('have.value', '2022-08-03T00:39');

      cy.get(`[data-cy="updatedBy"]`).type('75953').should('have.value', '75953');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T02:24').should('have.value', '2022-08-03T02:24');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        deviceLevel = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', deviceLevelPageUrlPattern);
    });
  });
});
