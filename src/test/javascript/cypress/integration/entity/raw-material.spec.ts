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

describe('RawMaterial e2e test', () => {
  const rawMaterialPageUrl = '/raw-material';
  const rawMaterialPageUrlPattern = new RegExp('/raw-material(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rawMaterialSample = {};

  let rawMaterial: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/raw-materials+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/raw-materials').as('postEntityRequest');
    cy.intercept('DELETE', '/api/raw-materials/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rawMaterial) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/raw-materials/${rawMaterial.id}`,
      }).then(() => {
        rawMaterial = undefined;
      });
    }
  });

  it('RawMaterials menu should load RawMaterials page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('raw-material');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RawMaterial').should('exist');
    cy.url().should('match', rawMaterialPageUrlPattern);
  });

  describe('RawMaterial page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rawMaterialPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RawMaterial page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/raw-material/new$'));
        cy.getEntityCreateUpdateHeading('RawMaterial');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', rawMaterialPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/raw-materials',
          body: rawMaterialSample,
        }).then(({ body }) => {
          rawMaterial = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/raw-materials+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [rawMaterial],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(rawMaterialPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RawMaterial page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rawMaterial');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', rawMaterialPageUrlPattern);
      });

      it('edit button click should load edit RawMaterial page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RawMaterial');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', rawMaterialPageUrlPattern);
      });

      it('last delete button click should delete instance of RawMaterial', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('rawMaterial').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', rawMaterialPageUrlPattern);

        rawMaterial = undefined;
      });
    });
  });

  describe('new RawMaterial page', () => {
    beforeEach(() => {
      cy.visit(`${rawMaterialPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RawMaterial');
    });

    it('should create an instance of RawMaterial', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('33b53d91-3457-4343-95c7-228a8be7e520')
        .invoke('val')
        .should('match', new RegExp('33b53d91-3457-4343-95c7-228a8be7e520'));

      cy.get(`[data-cy="quantity"]`).type('14707').should('have.value', '14707');

      cy.get(`[data-cy="uOM"]`).type('4411').should('have.value', '4411');

      cy.get(`[data-cy="materialType"]`).select('Fruits');

      cy.get(`[data-cy="price"]`).type('81937').should('have.value', '81937');

      cy.get(`[data-cy="description"]`).type('withdrawal Pike regional').should('have.value', 'withdrawal Pike regional');

      cy.get(`[data-cy="createdBy"]`).type('80200').should('have.value', '80200');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T02:43').should('have.value', '2022-08-03T02:43');

      cy.get(`[data-cy="updatedBy"]`).type('33025').should('have.value', '33025');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T04:35').should('have.value', '2022-08-03T04:35');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        rawMaterial = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', rawMaterialPageUrlPattern);
    });
  });
});
