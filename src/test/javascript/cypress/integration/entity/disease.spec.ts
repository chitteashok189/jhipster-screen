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

describe('Disease e2e test', () => {
  const diseasePageUrl = '/disease';
  const diseasePageUrlPattern = new RegExp('/disease(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const diseaseSample = {};

  let disease: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/diseases+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/diseases').as('postEntityRequest');
    cy.intercept('DELETE', '/api/diseases/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (disease) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/diseases/${disease.id}`,
      }).then(() => {
        disease = undefined;
      });
    }
  });

  it('Diseases menu should load Diseases page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('disease');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Disease').should('exist');
    cy.url().should('match', diseasePageUrlPattern);
  });

  describe('Disease page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(diseasePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Disease page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/disease/new$'));
        cy.getEntityCreateUpdateHeading('Disease');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseasePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/diseases',
          body: diseaseSample,
        }).then(({ body }) => {
          disease = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/diseases+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [disease],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(diseasePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Disease page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('disease');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseasePageUrlPattern);
      });

      it('edit button click should load edit Disease page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Disease');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseasePageUrlPattern);
      });

      it('last delete button click should delete instance of Disease', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('disease').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseasePageUrlPattern);

        disease = undefined;
      });
    });
  });

  describe('new Disease page', () => {
    beforeEach(() => {
      cy.visit(`${diseasePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Disease');
    });

    it('should create an instance of Disease', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('9117c056-dee4-4ad5-a89c-e89aa2b5ad1e')
        .invoke('val')
        .should('match', new RegExp('9117c056-dee4-4ad5-a89c-e89aa2b5ad1e'));

      cy.get(`[data-cy="threatLevel"]`).select('Moderate');

      cy.get(`[data-cy="name"]`).type('online Factors').should('have.value', 'online Factors');

      cy.get(`[data-cy="causalOrganism"]`).type('connect Extensions').should('have.value', 'connect Extensions');

      cy.get(`[data-cy="attachments"]`).type('98683').should('have.value', '98683');

      cy.get(`[data-cy="createdBy"]`).type('81449').should('have.value', '81449');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T04:38').should('have.value', '2022-08-03T04:38');

      cy.get(`[data-cy="updatedBy"]`).type('62636').should('have.value', '62636');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T07:03').should('have.value', '2022-08-03T07:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        disease = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', diseasePageUrlPattern);
    });
  });
});
