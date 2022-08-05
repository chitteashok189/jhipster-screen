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

describe('Zone e2e test', () => {
  const zonePageUrl = '/zone';
  const zonePageUrlPattern = new RegExp('/zone(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const zoneSample = {};

  let zone: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/zones+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/zones').as('postEntityRequest');
    cy.intercept('DELETE', '/api/zones/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (zone) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/zones/${zone.id}`,
      }).then(() => {
        zone = undefined;
      });
    }
  });

  it('Zones menu should load Zones page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('zone');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Zone').should('exist');
    cy.url().should('match', zonePageUrlPattern);
  });

  describe('Zone page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(zonePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Zone page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/zone/new$'));
        cy.getEntityCreateUpdateHeading('Zone');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', zonePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/zones',
          body: zoneSample,
        }).then(({ body }) => {
          zone = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/zones+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [zone],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(zonePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Zone page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('zone');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', zonePageUrlPattern);
      });

      it('edit button click should load edit Zone page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Zone');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', zonePageUrlPattern);
      });

      it('last delete button click should delete instance of Zone', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('zone').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', zonePageUrlPattern);

        zone = undefined;
      });
    });
  });

  describe('new Zone page', () => {
    beforeEach(() => {
      cy.visit(`${zonePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Zone');
    });

    it('should create an instance of Zone', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('6b707237-7c7d-48d7-83e8-a564c7ddfd2d')
        .invoke('val')
        .should('match', new RegExp('6b707237-7c7d-48d7-83e8-a564c7ddfd2d'));

      cy.get(`[data-cy="zoneType"]`).select('Tubers');

      cy.get(`[data-cy="zoneName"]`).type('24/365').should('have.value', '24/365');

      cy.get(`[data-cy="createdBy"]`).type('16885').should('have.value', '16885');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T05:49').should('have.value', '2022-08-03T05:49');

      cy.get(`[data-cy="updatedBy"]`).type('6298').should('have.value', '6298');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T03:42').should('have.value', '2022-08-03T03:42');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        zone = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', zonePageUrlPattern);
    });
  });
});
