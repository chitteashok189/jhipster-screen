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

describe('Nutrient e2e test', () => {
  const nutrientPageUrl = '/nutrient';
  const nutrientPageUrlPattern = new RegExp('/nutrient(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nutrientSample = {};

  let nutrient: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nutrients+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nutrients').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nutrients/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (nutrient) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nutrients/${nutrient.id}`,
      }).then(() => {
        nutrient = undefined;
      });
    }
  });

  it('Nutrients menu should load Nutrients page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nutrient');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Nutrient').should('exist');
    cy.url().should('match', nutrientPageUrlPattern);
  });

  describe('Nutrient page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nutrientPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Nutrient page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nutrient/new$'));
        cy.getEntityCreateUpdateHeading('Nutrient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nutrientPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nutrients',
          body: nutrientSample,
        }).then(({ body }) => {
          nutrient = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nutrients+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [nutrient],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(nutrientPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Nutrient page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nutrient');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nutrientPageUrlPattern);
      });

      it('edit button click should load edit Nutrient page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Nutrient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nutrientPageUrlPattern);
      });

      it('last delete button click should delete instance of Nutrient', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('nutrient').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nutrientPageUrlPattern);

        nutrient = undefined;
      });
    });
  });

  describe('new Nutrient page', () => {
    beforeEach(() => {
      cy.visit(`${nutrientPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Nutrient');
    });

    it('should create an instance of Nutrient', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('bf8cb23a-e71c-40ac-bcb7-2bda5d41fbf2')
        .invoke('val')
        .should('match', new RegExp('bf8cb23a-e71c-40ac-bcb7-2bda5d41fbf2'));

      cy.get(`[data-cy="nutrientName"]`).type('Rubber').should('have.value', 'Rubber');

      cy.get(`[data-cy="type"]`).select('Synthetic');

      cy.get(`[data-cy="brandName"]`).type('Cheese indigo Maryland').should('have.value', 'Cheese indigo Maryland');

      cy.get(`[data-cy="nutrientLabel"]`).type('71113').should('have.value', '71113');

      cy.get(`[data-cy="nutrientForms"]`).select('Fertilizers');

      cy.get(`[data-cy="nutrientTypeID"]`).select('Inorganic');

      cy.get(`[data-cy="price"]`).type('84714').should('have.value', '84714');

      cy.get(`[data-cy="createdBy"]`).type('24484').should('have.value', '24484');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T06:52').should('have.value', '2022-08-03T06:52');

      cy.get(`[data-cy="updatedBy"]`).type('37386').should('have.value', '37386');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T09:31').should('have.value', '2022-08-02T09:31');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        nutrient = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', nutrientPageUrlPattern);
    });
  });
});
