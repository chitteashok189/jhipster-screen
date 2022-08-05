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

describe('Alert e2e test', () => {
  const alertPageUrl = '/alert';
  const alertPageUrlPattern = new RegExp('/alert(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const alertSample = {};

  let alert: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/alerts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/alerts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/alerts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (alert) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/alerts/${alert.id}`,
      }).then(() => {
        alert = undefined;
      });
    }
  });

  it('Alerts menu should load Alerts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('alert');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Alert').should('exist');
    cy.url().should('match', alertPageUrlPattern);
  });

  describe('Alert page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(alertPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Alert page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/alert/new$'));
        cy.getEntityCreateUpdateHeading('Alert');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', alertPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/alerts',
          body: alertSample,
        }).then(({ body }) => {
          alert = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/alerts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [alert],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(alertPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Alert page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('alert');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', alertPageUrlPattern);
      });

      it('edit button click should load edit Alert page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Alert');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', alertPageUrlPattern);
      });

      it('last delete button click should delete instance of Alert', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('alert').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', alertPageUrlPattern);

        alert = undefined;
      });
    });
  });

  describe('new Alert page', () => {
    beforeEach(() => {
      cy.visit(`${alertPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Alert');
    });

    it('should create an instance of Alert', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('087f3119-9fe2-48ba-aa9d-fc24fca86269')
        .invoke('val')
        .should('match', new RegExp('087f3119-9fe2-48ba-aa9d-fc24fca86269'));

      cy.get(`[data-cy="name"]`).type('Kwanza').should('have.value', 'Kwanza');

      cy.get(`[data-cy="alertType"]`).select('Humidity');

      cy.get(`[data-cy="description"]`).type('withdrawal Fall invoice').should('have.value', 'withdrawal Fall invoice');

      cy.get(`[data-cy="datePeriod"]`).type('48936').should('have.value', '48936');

      cy.get(`[data-cy="durationDays"]`).type('64832').should('have.value', '64832');

      cy.get(`[data-cy="minimumTemperature"]`).type('6045').should('have.value', '6045');

      cy.get(`[data-cy="maximumTemperature"]`).type('45345').should('have.value', '45345');

      cy.get(`[data-cy="minHumidity"]`).type('73433').should('have.value', '73433');

      cy.get(`[data-cy="maxHumidity"]`).type('25761').should('have.value', '25761');

      cy.get(`[data-cy="precipitationType"]`).select('Sleet');

      cy.get(`[data-cy="minPrecipitation"]`).type('84414').should('have.value', '84414');

      cy.get(`[data-cy="maxPrecipitation"]`).type('19508').should('have.value', '19508');

      cy.get(`[data-cy="status"]`).select('Active');

      cy.get(`[data-cy="remediation"]`).select('Automatic');

      cy.get(`[data-cy="farmAssigned"]`).type('Integrated generate GB').should('have.value', 'Integrated generate GB');

      cy.get(`[data-cy="createdBy"]`).type('78474').should('have.value', '78474');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T20:59').should('have.value', '2022-08-02T20:59');

      cy.get(`[data-cy="updatedBy"]`).type('64546').should('have.value', '64546');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T04:47').should('have.value', '2022-08-03T04:47');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        alert = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', alertPageUrlPattern);
    });
  });
});
