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

describe('Device e2e test', () => {
  const devicePageUrl = '/device';
  const devicePageUrlPattern = new RegExp('/device(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const deviceSample = {};

  let device: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/devices+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/devices').as('postEntityRequest');
    cy.intercept('DELETE', '/api/devices/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (device) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/devices/${device.id}`,
      }).then(() => {
        device = undefined;
      });
    }
  });

  it('Devices menu should load Devices page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('device');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Device').should('exist');
    cy.url().should('match', devicePageUrlPattern);
  });

  describe('Device page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(devicePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Device page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/device/new$'));
        cy.getEntityCreateUpdateHeading('Device');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', devicePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/devices',
          body: deviceSample,
        }).then(({ body }) => {
          device = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/devices+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [device],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(devicePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Device page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('device');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', devicePageUrlPattern);
      });

      it('edit button click should load edit Device page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Device');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', devicePageUrlPattern);
      });

      it('last delete button click should delete instance of Device', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('device').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', devicePageUrlPattern);

        device = undefined;
      });
    });
  });

  describe('new Device page', () => {
    beforeEach(() => {
      cy.visit(`${devicePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Device');
    });

    it('should create an instance of Device', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('12ed504d-91ad-4f97-9848-b18316942bd5')
        .invoke('val')
        .should('match', new RegExp('12ed504d-91ad-4f97-9848-b18316942bd5'));

      cy.get(`[data-cy="deviceModel"]`).type('Usability Ergonomic').should('have.value', 'Usability Ergonomic');

      cy.get(`[data-cy="deviceAsset"]`).type('Frozen generate').should('have.value', 'Frozen generate');

      cy.get(`[data-cy="deviceType"]`).select('Output');

      cy.get(`[data-cy="hardwareID"]`).type('3935').should('have.value', '3935');

      cy.get(`[data-cy="reportingIntervalLocation"]`).type('62245').should('have.value', '62245');

      cy.get(`[data-cy="parentDevice"]`).type('firewall analyzer Fish').should('have.value', 'firewall analyzer Fish');

      cy.get(`[data-cy="properties"]`).type('Games 1080p magnetic').should('have.value', 'Games 1080p magnetic');

      cy.get(`[data-cy="description"]`).type('copying Dinar').should('have.value', 'copying Dinar');

      cy.get(`[data-cy="createdBy"]`).type('92085').should('have.value', '92085');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T09:56').should('have.value', '2022-08-02T09:56');

      cy.get(`[data-cy="updatedBy"]`).type('5437').should('have.value', '5437');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T21:05').should('have.value', '2022-08-02T21:05');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        device = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', devicePageUrlPattern);
    });
  });
});
