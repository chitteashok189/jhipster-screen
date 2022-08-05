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

describe('Inspection e2e test', () => {
  const inspectionPageUrl = '/inspection';
  const inspectionPageUrlPattern = new RegExp('/inspection(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const inspectionSample = {};

  let inspection: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/inspections+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/inspections').as('postEntityRequest');
    cy.intercept('DELETE', '/api/inspections/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (inspection) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/inspections/${inspection.id}`,
      }).then(() => {
        inspection = undefined;
      });
    }
  });

  it('Inspections menu should load Inspections page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('inspection');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Inspection').should('exist');
    cy.url().should('match', inspectionPageUrlPattern);
  });

  describe('Inspection page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(inspectionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Inspection page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/inspection/new$'));
        cy.getEntityCreateUpdateHeading('Inspection');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/inspections',
          body: inspectionSample,
        }).then(({ body }) => {
          inspection = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/inspections+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [inspection],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(inspectionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Inspection page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('inspection');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionPageUrlPattern);
      });

      it('edit button click should load edit Inspection page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Inspection');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionPageUrlPattern);
      });

      it('last delete button click should delete instance of Inspection', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('inspection').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionPageUrlPattern);

        inspection = undefined;
      });
    });
  });

  describe('new Inspection page', () => {
    beforeEach(() => {
      cy.visit(`${inspectionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Inspection');
    });

    it('should create an instance of Inspection', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('01d39f3b-9b33-485c-9f97-ca3e5b144b60')
        .invoke('val')
        .should('match', new RegExp('01d39f3b-9b33-485c-9f97-ca3e5b144b60'));

      cy.get(`[data-cy="inspectionSize"]`).type('67369').should('have.value', '67369');

      cy.get(`[data-cy="shape"]`).type('88155').should('have.value', '88155');

      cy.get(`[data-cy="wholeness"]`).type('45397').should('have.value', '45397');

      cy.get(`[data-cy="gloss"]`).type('9395').should('have.value', '9395');

      cy.get(`[data-cy="consistency"]`).type('85934').should('have.value', '85934');

      cy.get(`[data-cy="defects"]`).select('Bruises');

      cy.get(`[data-cy="colour"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="texture"]`).select('Lumpy');

      cy.get(`[data-cy="aroma"]`).select('Ripeness');

      cy.get(`[data-cy="flavour"]`).select('Sour');

      cy.get(`[data-cy="nutritionalValue"]`).type('86579').should('have.value', '86579');

      cy.get(`[data-cy="nutritionalValueType"]`).select('Polyphenolics');

      cy.get(`[data-cy="createdBy"]`).type('12075').should('have.value', '12075');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T19:08').should('have.value', '2022-08-02T19:08');

      cy.get(`[data-cy="updatedBy"]`).type('57260').should('have.value', '57260');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T20:03').should('have.value', '2022-08-02T20:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        inspection = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', inspectionPageUrlPattern);
    });
  });
});
