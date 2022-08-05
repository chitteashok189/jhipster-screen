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

describe('Climate e2e test', () => {
  const climatePageUrl = '/climate';
  const climatePageUrlPattern = new RegExp('/climate(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const climateSample = {};

  let climate: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/climates+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/climates').as('postEntityRequest');
    cy.intercept('DELETE', '/api/climates/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (climate) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/climates/${climate.id}`,
      }).then(() => {
        climate = undefined;
      });
    }
  });

  it('Climates menu should load Climates page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('climate');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Climate').should('exist');
    cy.url().should('match', climatePageUrlPattern);
  });

  describe('Climate page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(climatePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Climate page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/climate/new$'));
        cy.getEntityCreateUpdateHeading('Climate');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', climatePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/climates',
          body: climateSample,
        }).then(({ body }) => {
          climate = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/climates+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [climate],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(climatePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Climate page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('climate');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', climatePageUrlPattern);
      });

      it('edit button click should load edit Climate page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Climate');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', climatePageUrlPattern);
      });

      it('last delete button click should delete instance of Climate', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('climate').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', climatePageUrlPattern);

        climate = undefined;
      });
    });
  });

  describe('new Climate page', () => {
    beforeEach(() => {
      cy.visit(`${climatePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Climate');
    });

    it('should create an instance of Climate', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('713a216b-85ef-42ea-8f5e-a7f7c03f8ccd')
        .invoke('val')
        .should('match', new RegExp('713a216b-85ef-42ea-8f5e-a7f7c03f8ccd'));

      cy.get(`[data-cy="source"]`).select('Manual');

      cy.get(`[data-cy="airTemperature"]`).type('28079').should('have.value', '28079');

      cy.get(`[data-cy="relativeHumidity"]`).type('42952').should('have.value', '42952');

      cy.get(`[data-cy="vPD"]`).type('4828').should('have.value', '4828');

      cy.get(`[data-cy="evapotranspiration"]`).type('5652').should('have.value', '5652');

      cy.get(`[data-cy="barometricPressure"]`).type('83449').should('have.value', '83449');

      cy.get(`[data-cy="seaLevelPressure"]`).type('90369').should('have.value', '90369');

      cy.get(`[data-cy="co2Level"]`).type('27316').should('have.value', '27316');

      cy.get(`[data-cy="dewPoint"]`).type('29469').should('have.value', '29469');

      cy.get(`[data-cy="solarRadiation"]`).type('5360').should('have.value', '5360');

      cy.get(`[data-cy="heatIndex"]`).type('30316').should('have.value', '30316');

      cy.get(`[data-cy="createdBy"]`).type('37418').should('have.value', '37418');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T10:55').should('have.value', '2022-08-02T10:55');

      cy.get(`[data-cy="updatedBy"]`).type('25355').should('have.value', '25355');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T22:06').should('have.value', '2022-08-02T22:06');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        climate = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', climatePageUrlPattern);
    });
  });
});
