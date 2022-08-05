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

describe('PestControl e2e test', () => {
  const pestControlPageUrl = '/pest-control';
  const pestControlPageUrlPattern = new RegExp('/pest-control(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pestControlSample = {};

  let pestControl: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/pest-controls+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pest-controls').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pest-controls/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pestControl) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pest-controls/${pestControl.id}`,
      }).then(() => {
        pestControl = undefined;
      });
    }
  });

  it('PestControls menu should load PestControls page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pest-control');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PestControl').should('exist');
    cy.url().should('match', pestControlPageUrlPattern);
  });

  describe('PestControl page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pestControlPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PestControl page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pest-control/new$'));
        cy.getEntityCreateUpdateHeading('PestControl');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestControlPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pest-controls',
          body: pestControlSample,
        }).then(({ body }) => {
          pestControl = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pest-controls+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [pestControl],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(pestControlPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PestControl page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pestControl');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestControlPageUrlPattern);
      });

      it('edit button click should load edit PestControl page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PestControl');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestControlPageUrlPattern);
      });

      it('last delete button click should delete instance of PestControl', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('pestControl').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pestControlPageUrlPattern);

        pestControl = undefined;
      });
    });
  });

  describe('new PestControl page', () => {
    beforeEach(() => {
      cy.visit(`${pestControlPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PestControl');
    });

    it('should create an instance of PestControl', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('c3c4bb71-8f8c-4203-be34-c44643f87963')
        .invoke('val')
        .should('match', new RegExp('c3c4bb71-8f8c-4203-be34-c44643f87963'));

      cy.get(`[data-cy="natureOfDamage"]`).type('Investor Dollar online').should('have.value', 'Investor Dollar online');

      cy.get(`[data-cy="controlType"]`).select('Mechanical');

      cy.get(`[data-cy="controlMeasures"]`).type('Handcrafted').should('have.value', 'Handcrafted');

      cy.get(`[data-cy="createdBy"]`).type('39802').should('have.value', '39802');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T09:34').should('have.value', '2022-08-02T09:34');

      cy.get(`[data-cy="updatedBy"]`).type('29526').should('have.value', '29526');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T05:03').should('have.value', '2022-08-03T05:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        pestControl = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', pestControlPageUrlPattern);
    });
  });
});
