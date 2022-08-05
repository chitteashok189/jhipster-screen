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

describe('Ennumeration e2e test', () => {
  const ennumerationPageUrl = '/ennumeration';
  const ennumerationPageUrlPattern = new RegExp('/ennumeration(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ennumerationSample = {};

  let ennumeration: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ennumerations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ennumerations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ennumerations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ennumeration) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ennumerations/${ennumeration.id}`,
      }).then(() => {
        ennumeration = undefined;
      });
    }
  });

  it('Ennumerations menu should load Ennumerations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ennumeration');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Ennumeration').should('exist');
    cy.url().should('match', ennumerationPageUrlPattern);
  });

  describe('Ennumeration page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ennumerationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Ennumeration page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ennumeration/new$'));
        cy.getEntityCreateUpdateHeading('Ennumeration');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ennumerations',
          body: ennumerationSample,
        }).then(({ body }) => {
          ennumeration = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ennumerations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [ennumeration],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(ennumerationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Ennumeration page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ennumeration');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationPageUrlPattern);
      });

      it('edit button click should load edit Ennumeration page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Ennumeration');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationPageUrlPattern);
      });

      it('last delete button click should delete instance of Ennumeration', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('ennumeration').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationPageUrlPattern);

        ennumeration = undefined;
      });
    });
  });

  describe('new Ennumeration page', () => {
    beforeEach(() => {
      cy.visit(`${ennumerationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Ennumeration');
    });

    it('should create an instance of Ennumeration', () => {
      cy.get(`[data-cy="enumCode"]`).type('90678').should('have.value', '90678');

      cy.get(`[data-cy="description"]`).type('models Frozen').should('have.value', 'models Frozen');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        ennumeration = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', ennumerationPageUrlPattern);
    });
  });
});
