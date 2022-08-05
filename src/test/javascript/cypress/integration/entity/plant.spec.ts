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

describe('Plant e2e test', () => {
  const plantPageUrl = '/plant';
  const plantPageUrlPattern = new RegExp('/plant(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const plantSample = {};

  let plant: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/plants+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/plants').as('postEntityRequest');
    cy.intercept('DELETE', '/api/plants/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (plant) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/plants/${plant.id}`,
      }).then(() => {
        plant = undefined;
      });
    }
  });

  it('Plants menu should load Plants page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('plant');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Plant').should('exist');
    cy.url().should('match', plantPageUrlPattern);
  });

  describe('Plant page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(plantPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Plant page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/plant/new$'));
        cy.getEntityCreateUpdateHeading('Plant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/plants',
          body: plantSample,
        }).then(({ body }) => {
          plant = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/plants+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [plant],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(plantPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Plant page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('plant');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantPageUrlPattern);
      });

      it('edit button click should load edit Plant page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Plant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantPageUrlPattern);
      });

      it('last delete button click should delete instance of Plant', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('plant').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', plantPageUrlPattern);

        plant = undefined;
      });
    });
  });

  describe('new Plant page', () => {
    beforeEach(() => {
      cy.visit(`${plantPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Plant');
    });

    it('should create an instance of Plant', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('95cbf400-4290-4a80-917f-260e5cd6ac8c')
        .invoke('val')
        .should('match', new RegExp('95cbf400-4290-4a80-917f-260e5cd6ac8c'));

      cy.get(`[data-cy="commonName"]`).type('auxiliary Intranet Avon').should('have.value', 'auxiliary Intranet Avon');

      cy.get(`[data-cy="scientificName"]`).type('Principal').should('have.value', 'Principal');

      cy.get(`[data-cy="familyName"]`).type('Music Dynamic').should('have.value', 'Music Dynamic');

      cy.get(`[data-cy="plantSpacing"]`).type('6030').should('have.value', '6030');

      cy.get(`[data-cy="seedingMonth"]`).select('April');

      cy.get(`[data-cy="transplantMonth"]`).select('November');

      cy.get(`[data-cy="harvestMonth"]`).select('August');

      cy.get(`[data-cy="originCountry"]`).type('65608').should('have.value', '65608');

      cy.get(`[data-cy="yearlyCrops"]`).type('29658').should('have.value', '29658');

      cy.get(`[data-cy="nativeTemperature"]`).type('16480').should('have.value', '16480');

      cy.get(`[data-cy="nativeHumidity"]`).type('87330').should('have.value', '87330');

      cy.get(`[data-cy="nativeDayDuration"]`).type('58161').should('have.value', '58161');

      cy.get(`[data-cy="nativeNightDuration"]`).type('78572').should('have.value', '78572');

      cy.get(`[data-cy="nativeSoilMoisture"]`).type('40803').should('have.value', '40803');

      cy.get(`[data-cy="plantingPeriod"]`).type('5916').should('have.value', '5916');

      cy.get(`[data-cy="yieldUnit"]`).type('41659').should('have.value', '41659');

      cy.get(`[data-cy="growthHeightMin"]`).type('32562').should('have.value', '32562');

      cy.get(`[data-cy="growthHeightMax"]`).type('45208').should('have.value', '45208');

      cy.get(`[data-cy="grownSpreadMin"]`).type('43254').should('have.value', '43254');

      cy.get(`[data-cy="grownSpreadMax"]`).type('9505').should('have.value', '9505');

      cy.get(`[data-cy="grownWeightMax"]`).type('52275').should('have.value', '52275');

      cy.get(`[data-cy="grownWeightMin"]`).type('5961').should('have.value', '5961');

      cy.get(`[data-cy="growingMedia"]`).type('Avon function haptic').should('have.value', 'Avon function haptic');

      cy.get(`[data-cy="documents"]`).type('Israel').should('have.value', 'Israel');

      cy.get(`[data-cy="notes"]`).type('SSL').should('have.value', 'SSL');

      cy.setFieldImageAsBytesOfEntity('attachments', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="createdBy"]`).type('41293').should('have.value', '41293');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T20:49').should('have.value', '2022-08-02T20:49');

      cy.get(`[data-cy="updatedBy"]`).type('55498').should('have.value', '55498');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T19:07').should('have.value', '2022-08-02T19:07');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        plant = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', plantPageUrlPattern);
    });
  });
});
