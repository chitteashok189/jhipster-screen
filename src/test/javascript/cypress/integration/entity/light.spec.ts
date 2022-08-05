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

describe('Light e2e test', () => {
  const lightPageUrl = '/light';
  const lightPageUrlPattern = new RegExp('/light(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const lightSample = {};

  let light: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/lights+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/lights').as('postEntityRequest');
    cy.intercept('DELETE', '/api/lights/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (light) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/lights/${light.id}`,
      }).then(() => {
        light = undefined;
      });
    }
  });

  it('Lights menu should load Lights page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('light');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Light').should('exist');
    cy.url().should('match', lightPageUrlPattern);
  });

  describe('Light page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(lightPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Light page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/light/new$'));
        cy.getEntityCreateUpdateHeading('Light');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lightPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/lights',
          body: lightSample,
        }).then(({ body }) => {
          light = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/lights+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [light],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(lightPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Light page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('light');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lightPageUrlPattern);
      });

      it('edit button click should load edit Light page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Light');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lightPageUrlPattern);
      });

      it('last delete button click should delete instance of Light', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('light').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lightPageUrlPattern);

        light = undefined;
      });
    });
  });

  describe('new Light page', () => {
    beforeEach(() => {
      cy.visit(`${lightPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Light');
    });

    it('should create an instance of Light', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('2d937848-c009-463f-b04d-3b45a1720a97')
        .invoke('val')
        .should('match', new RegExp('2d937848-c009-463f-b04d-3b45a1720a97'));

      cy.get(`[data-cy="source"]`).select('Manual');

      cy.get(`[data-cy="lightIntensity"]`).type('28010').should('have.value', '28010');

      cy.get(`[data-cy="dailyLightIntegral"]`).type('58293').should('have.value', '58293');

      cy.get(`[data-cy="pAR"]`).type('37619').should('have.value', '37619');

      cy.get(`[data-cy="pPFD"]`).type('77148').should('have.value', '77148');

      cy.get(`[data-cy="createdBy"]`).type('21890').should('have.value', '21890');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T06:10').should('have.value', '2022-08-03T06:10');

      cy.get(`[data-cy="updatedBy"]`).type('2361').should('have.value', '2361');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T02:57').should('have.value', '2022-08-03T02:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        light = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', lightPageUrlPattern);
    });
  });
});
