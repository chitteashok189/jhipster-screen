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

describe('Recipe e2e test', () => {
  const recipePageUrl = '/recipe';
  const recipePageUrlPattern = new RegExp('/recipe(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const recipeSample = {};

  let recipe: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/recipes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/recipes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/recipes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (recipe) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/recipes/${recipe.id}`,
      }).then(() => {
        recipe = undefined;
      });
    }
  });

  it('Recipes menu should load Recipes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('recipe');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Recipe').should('exist');
    cy.url().should('match', recipePageUrlPattern);
  });

  describe('Recipe page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(recipePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Recipe page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/recipe/new$'));
        cy.getEntityCreateUpdateHeading('Recipe');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recipePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/recipes',
          body: recipeSample,
        }).then(({ body }) => {
          recipe = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/recipes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [recipe],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(recipePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Recipe page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('recipe');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recipePageUrlPattern);
      });

      it('edit button click should load edit Recipe page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Recipe');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recipePageUrlPattern);
      });

      it('last delete button click should delete instance of Recipe', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('recipe').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recipePageUrlPattern);

        recipe = undefined;
      });
    });
  });

  describe('new Recipe page', () => {
    beforeEach(() => {
      cy.visit(`${recipePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Recipe');
    });

    it('should create an instance of Recipe', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('07687af0-df6d-4f1d-93ea-0edf162cbfcd')
        .invoke('val')
        .should('match', new RegExp('07687af0-df6d-4f1d-93ea-0edf162cbfcd'));

      cy.get(`[data-cy="plantName"]`).type('Computers Fish').should('have.value', 'Computers Fish');

      cy.get(`[data-cy="recipeType"]`).type('compress Rustic').should('have.value', 'compress Rustic');

      cy.get(`[data-cy="pHMin"]`).type('56126').should('have.value', '56126');

      cy.get(`[data-cy="pHMax"]`).type('58773').should('have.value', '58773');

      cy.get(`[data-cy="ecMin"]`).select('Arboriculture');

      cy.get(`[data-cy="eCMax"]`).type('43478').should('have.value', '43478');

      cy.get(`[data-cy="airTempMax"]`).type('74304').should('have.value', '74304');

      cy.get(`[data-cy="airTempMin"]`).type('2321').should('have.value', '2321');

      cy.get(`[data-cy="humidityMax"]`).type('29909').should('have.value', '29909');

      cy.get(`[data-cy="humidityMin"]`).type('36562').should('have.value', '36562');

      cy.get(`[data-cy="nutrientTempMax"]`).type('3758').should('have.value', '3758');

      cy.get(`[data-cy="nutrientTempMin"]`).type('28757').should('have.value', '28757');

      cy.get(`[data-cy="luxGermMax"]`).type('10768').should('have.value', '10768');

      cy.get(`[data-cy="luxGermMin"]`).type('94314').should('have.value', '94314');

      cy.get(`[data-cy="lightGermDor"]`).type('84428').should('have.value', '84428');

      cy.get(`[data-cy="lightGermCycle"]`).type('31377').should('have.value', '31377');

      cy.get(`[data-cy="luxGrowMax"]`).type('71585').should('have.value', '71585');

      cy.get(`[data-cy="luxGrowMin"]`).type('77229').should('have.value', '77229');

      cy.get(`[data-cy="lightGrowDor"]`).type('57522').should('have.value', '57522');

      cy.get(`[data-cy="lightGrowCycle"]`).type('5091').should('have.value', '5091');

      cy.get(`[data-cy="co2LightMax"]`).type('13467').should('have.value', '13467');

      cy.get(`[data-cy="co2LightMin"]`).type('56953').should('have.value', '56953');

      cy.get(`[data-cy="co2DarkMax"]`).type('51929').should('have.value', '51929');

      cy.get(`[data-cy="co2DarkMin"]`).type('43790').should('have.value', '43790');

      cy.get(`[data-cy="dOMax"]`).type('73134').should('have.value', '73134');

      cy.get(`[data-cy="dOMin"]`).type('99880').should('have.value', '99880');

      cy.get(`[data-cy="mediaMoistureMax"]`).type('69123').should('have.value', '69123');

      cy.get(`[data-cy="mediaMoistureMin"]`).type('94320').should('have.value', '94320');

      cy.get(`[data-cy="nitrogen"]`).type('4180').should('have.value', '4180');

      cy.get(`[data-cy="phosphorus"]`).type('56241').should('have.value', '56241');

      cy.get(`[data-cy="potassium"]`).type('71339').should('have.value', '71339');

      cy.get(`[data-cy="sulphur"]`).type('63556').should('have.value', '63556');

      cy.get(`[data-cy="calcium"]`).type('30690').should('have.value', '30690');

      cy.get(`[data-cy="magnesium"]`).type('7960').should('have.value', '7960');

      cy.get(`[data-cy="manganese"]`).type('80913').should('have.value', '80913');

      cy.get(`[data-cy="iron"]`).type('35668').should('have.value', '35668');

      cy.get(`[data-cy="boron"]`).type('97060').should('have.value', '97060');

      cy.get(`[data-cy="copper"]`).type('25467').should('have.value', '25467');

      cy.get(`[data-cy="zinc"]`).type('97760').should('have.value', '97760');

      cy.get(`[data-cy="molybdenum"]`).type('55197').should('have.value', '55197');

      cy.get(`[data-cy="germinationTAT"]`).type('37341').should('have.value', '37341');

      cy.get(`[data-cy="identificationComment"]`).type('Automotive zero').should('have.value', 'Automotive zero');

      cy.get(`[data-cy="growingComment"]`).type('array Investment').should('have.value', 'array Investment');

      cy.get(`[data-cy="usageComment"]`).type('synthesize navigating Dynamic').should('have.value', 'synthesize navigating Dynamic');

      cy.get(`[data-cy="createdBy"]`).type('83527').should('have.value', '83527');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T07:49').should('have.value', '2022-08-03T07:49');

      cy.get(`[data-cy="updatedBy"]`).type('47832').should('have.value', '47832');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T07:57').should('have.value', '2022-08-03T07:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        recipe = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', recipePageUrlPattern);
    });
  });
});
