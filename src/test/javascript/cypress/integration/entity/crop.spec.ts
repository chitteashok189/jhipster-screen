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

describe('Crop e2e test', () => {
  const cropPageUrl = '/crop';
  const cropPageUrlPattern = new RegExp('/crop(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cropSample = {};

  let crop: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/crops+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/crops').as('postEntityRequest');
    cy.intercept('DELETE', '/api/crops/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (crop) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/crops/${crop.id}`,
      }).then(() => {
        crop = undefined;
      });
    }
  });

  it('Crops menu should load Crops page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crop');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Crop').should('exist');
    cy.url().should('match', cropPageUrlPattern);
  });

  describe('Crop page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cropPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Crop page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crop/new$'));
        cy.getEntityCreateUpdateHeading('Crop');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cropPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/crops',
          body: cropSample,
        }).then(({ body }) => {
          crop = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/crops+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [crop],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(cropPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Crop page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('crop');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cropPageUrlPattern);
      });

      it('edit button click should load edit Crop page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Crop');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cropPageUrlPattern);
      });

      it('last delete button click should delete instance of Crop', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('crop').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cropPageUrlPattern);

        crop = undefined;
      });
    });
  });

  describe('new Crop page', () => {
    beforeEach(() => {
      cy.visit(`${cropPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Crop');
    });

    it('should create an instance of Crop', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('27020fc8-c98c-442e-b0b8-927684642c0e')
        .invoke('val')
        .should('match', new RegExp('27020fc8-c98c-442e-b0b8-927684642c0e'));

      cy.get(`[data-cy="cropCode"]`).type('Electronics Specialist').should('have.value', 'Electronics Specialist');

      cy.get(`[data-cy="cropName"]`).type('hour').should('have.value', 'hour');

      cy.get(`[data-cy="cropType"]`).select('Food');

      cy.get(`[data-cy="horticultureType"]`).select('Viticulture');

      cy.get(`[data-cy="isHybrid"]`).should('not.be.checked');
      cy.get(`[data-cy="isHybrid"]`).click().should('be.checked');

      cy.get(`[data-cy="cultivar"]`).type('Alabama Customizable').should('have.value', 'Alabama Customizable');

      cy.get(`[data-cy="sowingDate"]`).type('2022-08-02T12:42').should('have.value', '2022-08-02T12:42');

      cy.get(`[data-cy="sowingDepth"]`).type('54689').should('have.value', '54689');

      cy.get(`[data-cy="rowSpacingMax"]`).type('17251').should('have.value', '17251');

      cy.get(`[data-cy="rowSpacingMin"]`).type('24689').should('have.value', '24689');

      cy.get(`[data-cy="seedDepthMax"]`).type('41399').should('have.value', '41399');

      cy.get(`[data-cy="seedDepthMin"]`).type('35861').should('have.value', '35861');

      cy.get(`[data-cy="seedSpacingMax"]`).type('34300').should('have.value', '34300');

      cy.get(`[data-cy="seedSpacingMin"]`).type('76205').should('have.value', '76205');

      cy.get(`[data-cy="yearlyCrops"]`).type('427').should('have.value', '427');

      cy.get(`[data-cy="growingSeason"]`).type('Manat Indiana Credit').should('have.value', 'Manat Indiana Credit');

      cy.get(`[data-cy="growingPhase"]`).select('Mid');

      cy.get(`[data-cy="plantingDate"]`).type('2022-08-02T23:57').should('have.value', '2022-08-02T23:57');

      cy.get(`[data-cy="plantSpacing"]`).type('59751').should('have.value', '59751');

      cy.get(`[data-cy="plantingMaterial"]`).select('Cuttings');

      cy.get(`[data-cy="transplantationDate"]`).type('2022-08-02T12:13').should('have.value', '2022-08-02T12:13');

      cy.get(`[data-cy="fertigationscheduleID"]`).type('16976').should('have.value', '16976');

      cy.get(`[data-cy="plannedYield"]`).type('3425').should('have.value', '3425');

      cy.get(`[data-cy="actualYield"]`).type('81147').should('have.value', '81147');

      cy.get(`[data-cy="yieldUnit"]`).type('51675').should('have.value', '51675');

      cy.get(`[data-cy="createdBy"]`).type('20531').should('have.value', '20531');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T07:15').should('have.value', '2022-08-03T07:15');

      cy.get(`[data-cy="updatedBy"]`).type('78227').should('have.value', '78227');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T14:17').should('have.value', '2022-08-02T14:17');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        crop = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', cropPageUrlPattern);
    });
  });
});
