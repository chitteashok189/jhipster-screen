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

describe('Season e2e test', () => {
  const seasonPageUrl = '/season';
  const seasonPageUrlPattern = new RegExp('/season(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const seasonSample = {};

  let season: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/seasons+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/seasons').as('postEntityRequest');
    cy.intercept('DELETE', '/api/seasons/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (season) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/seasons/${season.id}`,
      }).then(() => {
        season = undefined;
      });
    }
  });

  it('Seasons menu should load Seasons page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('season');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Season').should('exist');
    cy.url().should('match', seasonPageUrlPattern);
  });

  describe('Season page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(seasonPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Season page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/season/new$'));
        cy.getEntityCreateUpdateHeading('Season');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seasonPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/seasons',
          body: seasonSample,
        }).then(({ body }) => {
          season = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/seasons+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [season],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(seasonPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Season page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('season');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seasonPageUrlPattern);
      });

      it('edit button click should load edit Season page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Season');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seasonPageUrlPattern);
      });

      it('last delete button click should delete instance of Season', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('season').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', seasonPageUrlPattern);

        season = undefined;
      });
    });
  });

  describe('new Season page', () => {
    beforeEach(() => {
      cy.visit(`${seasonPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Season');
    });

    it('should create an instance of Season', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('37c2ca6c-1f03-4691-adba-4c8efdc6edb0')
        .invoke('val')
        .should('match', new RegExp('37c2ca6c-1f03-4691-adba-4c8efdc6edb0'));

      cy.get(`[data-cy="seasonType"]`).select('Kharif');

      cy.get(`[data-cy="cropName"]`).type('Garden').should('have.value', 'Garden');

      cy.get(`[data-cy="area"]`).type('29333').should('have.value', '29333');

      cy.get(`[data-cy="seasonTime"]`).select('July');

      cy.get(`[data-cy="seasonstartDate"]`).type('2022-08-02T09:39').should('have.value', '2022-08-02T09:39');

      cy.get(`[data-cy="seasonEndDate"]`).type('2022-08-02T15:54').should('have.value', '2022-08-02T15:54');

      cy.get(`[data-cy="createdBy"]`).type('16217').should('have.value', '16217');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T11:57').should('have.value', '2022-08-02T11:57');

      cy.get(`[data-cy="updatedBy"]`).type('130').should('have.value', '130');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T04:20').should('have.value', '2022-08-03T04:20');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        season = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', seasonPageUrlPattern);
    });
  });
});
