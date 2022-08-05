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

describe('Dosing e2e test', () => {
  const dosingPageUrl = '/dosing';
  const dosingPageUrlPattern = new RegExp('/dosing(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dosingSample = {};

  let dosing: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/dosings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/dosings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/dosings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dosing) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/dosings/${dosing.id}`,
      }).then(() => {
        dosing = undefined;
      });
    }
  });

  it('Dosings menu should load Dosings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('dosing');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Dosing').should('exist');
    cy.url().should('match', dosingPageUrlPattern);
  });

  describe('Dosing page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dosingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Dosing page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/dosing/new$'));
        cy.getEntityCreateUpdateHeading('Dosing');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dosingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/dosings',
          body: dosingSample,
        }).then(({ body }) => {
          dosing = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/dosings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [dosing],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(dosingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Dosing page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dosing');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dosingPageUrlPattern);
      });

      it('edit button click should load edit Dosing page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Dosing');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dosingPageUrlPattern);
      });

      it('last delete button click should delete instance of Dosing', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('dosing').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dosingPageUrlPattern);

        dosing = undefined;
      });
    });
  });

  describe('new Dosing page', () => {
    beforeEach(() => {
      cy.visit(`${dosingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Dosing');
    });

    it('should create an instance of Dosing', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('d10c0896-fe1c-403e-8d56-73347bf6eb92')
        .invoke('val')
        .should('match', new RegExp('d10c0896-fe1c-403e-8d56-73347bf6eb92'));

      cy.get(`[data-cy="source"]`).select('Manual');

      cy.get(`[data-cy="pH"]`).type('602').should('have.value', '602');

      cy.get(`[data-cy="eC"]`).type('67935').should('have.value', '67935');

      cy.get(`[data-cy="dO"]`).type('9739').should('have.value', '9739');

      cy.get(`[data-cy="nutrientTemperature"]`).type('82619').should('have.value', '82619');

      cy.get(`[data-cy="solarRadiation"]`).type('23865').should('have.value', '23865');

      cy.get(`[data-cy="createdBy"]`).type('41663').should('have.value', '41663');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T13:00').should('have.value', '2022-08-02T13:00');

      cy.get(`[data-cy="updatedBy"]`).type('68323').should('have.value', '68323');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T07:16').should('have.value', '2022-08-03T07:16');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        dosing = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', dosingPageUrlPattern);
    });
  });
});
