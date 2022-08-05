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

describe('Weather e2e test', () => {
  const weatherPageUrl = '/weather';
  const weatherPageUrlPattern = new RegExp('/weather(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const weatherSample = {};

  let weather: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/weathers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/weathers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/weathers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (weather) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/weathers/${weather.id}`,
      }).then(() => {
        weather = undefined;
      });
    }
  });

  it('Weathers menu should load Weathers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('weather');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Weather').should('exist');
    cy.url().should('match', weatherPageUrlPattern);
  });

  describe('Weather page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(weatherPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Weather page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/weather/new$'));
        cy.getEntityCreateUpdateHeading('Weather');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', weatherPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/weathers',
          body: weatherSample,
        }).then(({ body }) => {
          weather = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/weathers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [weather],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(weatherPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Weather page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('weather');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', weatherPageUrlPattern);
      });

      it('edit button click should load edit Weather page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Weather');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', weatherPageUrlPattern);
      });

      it('last delete button click should delete instance of Weather', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('weather').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', weatherPageUrlPattern);

        weather = undefined;
      });
    });
  });

  describe('new Weather page', () => {
    beforeEach(() => {
      cy.visit(`${weatherPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Weather');
    });

    it('should create an instance of Weather', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('ccda0764-4e44-4580-9f2b-b55e4998e734')
        .invoke('val')
        .should('match', new RegExp('ccda0764-4e44-4580-9f2b-b55e4998e734'));

      cy.get(`[data-cy="cityID"]`).type('56169').should('have.value', '56169');

      cy.get(`[data-cy="startTimestamp"]`).type('86236').should('have.value', '86236');

      cy.get(`[data-cy="endTimestamp"]`).type('50328').should('have.value', '50328');

      cy.get(`[data-cy="weatherStatusID"]`).type('68552').should('have.value', '68552');

      cy.get(`[data-cy="temperature"]`).type('36601').should('have.value', '36601');

      cy.get(`[data-cy="feelsLikeTemperature"]`).type('37100').should('have.value', '37100');

      cy.get(`[data-cy="humidity"]`).type('32317').should('have.value', '32317');

      cy.get(`[data-cy="windSpeed"]`).type('80968').should('have.value', '80968');

      cy.get(`[data-cy="windDirection"]`).type('99855').should('have.value', '99855');

      cy.get(`[data-cy="pressureinmmhg"]`).type('72186').should('have.value', '72186');

      cy.get(`[data-cy="visibilityinmph"]`).type('66348').should('have.value', '66348');

      cy.get(`[data-cy="cloudCover"]`).type('60962').should('have.value', '60962');

      cy.get(`[data-cy="precipitation"]`).type('66500').should('have.value', '66500');

      cy.get(`[data-cy="createdBy"]`).type('1070').should('have.value', '1070');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T18:11').should('have.value', '2022-08-02T18:11');

      cy.get(`[data-cy="updatedBy"]`).type('71480').should('have.value', '71480');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T22:36').should('have.value', '2022-08-02T22:36');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        weather = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', weatherPageUrlPattern);
    });
  });
});
