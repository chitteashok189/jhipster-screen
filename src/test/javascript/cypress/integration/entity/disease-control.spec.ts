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

describe('DiseaseControl e2e test', () => {
  const diseaseControlPageUrl = '/disease-control';
  const diseaseControlPageUrlPattern = new RegExp('/disease-control(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const diseaseControlSample = {};

  let diseaseControl: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/disease-controls+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/disease-controls').as('postEntityRequest');
    cy.intercept('DELETE', '/api/disease-controls/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (diseaseControl) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/disease-controls/${diseaseControl.id}`,
      }).then(() => {
        diseaseControl = undefined;
      });
    }
  });

  it('DiseaseControls menu should load DiseaseControls page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('disease-control');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DiseaseControl').should('exist');
    cy.url().should('match', diseaseControlPageUrlPattern);
  });

  describe('DiseaseControl page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(diseaseControlPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DiseaseControl page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/disease-control/new$'));
        cy.getEntityCreateUpdateHeading('DiseaseControl');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseaseControlPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/disease-controls',
          body: diseaseControlSample,
        }).then(({ body }) => {
          diseaseControl = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/disease-controls+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [diseaseControl],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(diseaseControlPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DiseaseControl page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('diseaseControl');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseaseControlPageUrlPattern);
      });

      it('edit button click should load edit DiseaseControl page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DiseaseControl');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseaseControlPageUrlPattern);
      });

      it('last delete button click should delete instance of DiseaseControl', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('diseaseControl').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', diseaseControlPageUrlPattern);

        diseaseControl = undefined;
      });
    });
  });

  describe('new DiseaseControl page', () => {
    beforeEach(() => {
      cy.visit(`${diseaseControlPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DiseaseControl');
    });

    it('should create an instance of DiseaseControl', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('9276abd8-e7a9-4655-a186-3893dd5e7c6c')
        .invoke('val')
        .should('match', new RegExp('9276abd8-e7a9-4655-a186-3893dd5e7c6c'));

      cy.get(`[data-cy="controlType"]`).select('Biological');

      cy.get(`[data-cy="treatment"]`).type('transmitter').should('have.value', 'transmitter');

      cy.get(`[data-cy="createdBy"]`).type('59773').should('have.value', '59773');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T08:02').should('have.value', '2022-08-03T08:02');

      cy.get(`[data-cy="updatedBy"]`).type('53110').should('have.value', '53110');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T06:57').should('have.value', '2022-08-03T06:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        diseaseControl = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', diseaseControlPageUrlPattern);
    });
  });
});
