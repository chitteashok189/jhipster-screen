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

describe('InspectionRecord e2e test', () => {
  const inspectionRecordPageUrl = '/inspection-record';
  const inspectionRecordPageUrlPattern = new RegExp('/inspection-record(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const inspectionRecordSample = {};

  let inspectionRecord: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/inspection-records+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/inspection-records').as('postEntityRequest');
    cy.intercept('DELETE', '/api/inspection-records/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (inspectionRecord) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/inspection-records/${inspectionRecord.id}`,
      }).then(() => {
        inspectionRecord = undefined;
      });
    }
  });

  it('InspectionRecords menu should load InspectionRecords page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('inspection-record');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('InspectionRecord').should('exist');
    cy.url().should('match', inspectionRecordPageUrlPattern);
  });

  describe('InspectionRecord page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(inspectionRecordPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create InspectionRecord page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/inspection-record/new$'));
        cy.getEntityCreateUpdateHeading('InspectionRecord');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionRecordPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/inspection-records',
          body: inspectionRecordSample,
        }).then(({ body }) => {
          inspectionRecord = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/inspection-records+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [inspectionRecord],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(inspectionRecordPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details InspectionRecord page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('inspectionRecord');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionRecordPageUrlPattern);
      });

      it('edit button click should load edit InspectionRecord page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InspectionRecord');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionRecordPageUrlPattern);
      });

      it('last delete button click should delete instance of InspectionRecord', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('inspectionRecord').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', inspectionRecordPageUrlPattern);

        inspectionRecord = undefined;
      });
    });
  });

  describe('new InspectionRecord page', () => {
    beforeEach(() => {
      cy.visit(`${inspectionRecordPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('InspectionRecord');
    });

    it('should create an instance of InspectionRecord', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('c204a534-5033-41ca-96bb-ce9396920ec1')
        .invoke('val')
        .should('match', new RegExp('c204a534-5033-41ca-96bb-ce9396920ec1'));

      cy.get(`[data-cy="rawMaterialBatchNo"]`).type('30327').should('have.value', '30327');

      cy.get(`[data-cy="productBatchNo"]`).type('23857').should('have.value', '23857');

      cy.get(`[data-cy="rawMaterialBatchCode"]`).type('62536').should('have.value', '62536');

      cy.get(`[data-cy="inputBatchNo"]`).type('24972').should('have.value', '24972');

      cy.get(`[data-cy="inputBatchCode"]`).type('88813').should('have.value', '88813');

      cy.setFieldImageAsBytesOfEntity('attachments', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="createdBy"]`).type('25426').should('have.value', '25426');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T18:01').should('have.value', '2022-08-02T18:01');

      cy.get(`[data-cy="updatedBy"]`).type('91006').should('have.value', '91006');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T17:15').should('have.value', '2022-08-02T17:15');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        inspectionRecord = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', inspectionRecordPageUrlPattern);
    });
  });
});
