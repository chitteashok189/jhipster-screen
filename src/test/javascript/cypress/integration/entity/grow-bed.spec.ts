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

describe('GrowBed e2e test', () => {
  const growBedPageUrl = '/grow-bed';
  const growBedPageUrlPattern = new RegExp('/grow-bed(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const growBedSample = {};

  let growBed: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/grow-beds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/grow-beds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/grow-beds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (growBed) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/grow-beds/${growBed.id}`,
      }).then(() => {
        growBed = undefined;
      });
    }
  });

  it('GrowBeds menu should load GrowBeds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('grow-bed');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('GrowBed').should('exist');
    cy.url().should('match', growBedPageUrlPattern);
  });

  describe('GrowBed page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(growBedPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create GrowBed page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/grow-bed/new$'));
        cy.getEntityCreateUpdateHeading('GrowBed');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', growBedPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/grow-beds',
          body: growBedSample,
        }).then(({ body }) => {
          growBed = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/grow-beds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [growBed],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(growBedPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details GrowBed page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('growBed');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', growBedPageUrlPattern);
      });

      it('edit button click should load edit GrowBed page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('GrowBed');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', growBedPageUrlPattern);
      });

      it('last delete button click should delete instance of GrowBed', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('growBed').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', growBedPageUrlPattern);

        growBed = undefined;
      });
    });
  });

  describe('new GrowBed page', () => {
    beforeEach(() => {
      cy.visit(`${growBedPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('GrowBed');
    });

    it('should create an instance of GrowBed', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('e6ac4ff6-3b35-4a05-9a4c-ade92d8eef05')
        .invoke('val')
        .should('match', new RegExp('e6ac4ff6-3b35-4a05-9a4c-ade92d8eef05'));

      cy.get(`[data-cy="growBedType"]`).select('Bench');

      cy.get(`[data-cy="growBedName"]`).type('Sports EXE Koruna').should('have.value', 'Sports EXE Koruna');

      cy.get(`[data-cy="manufacturer"]`).type('Creek Buckinghamshire').should('have.value', 'Creek Buckinghamshire');

      cy.get(`[data-cy="createdBy"]`).type('93652').should('have.value', '93652');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T17:39').should('have.value', '2022-08-02T17:39');

      cy.get(`[data-cy="updatedBy"]`).type('42338').should('have.value', '42338');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T00:54').should('have.value', '2022-08-03T00:54');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        growBed = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', growBedPageUrlPattern);
    });
  });
});
