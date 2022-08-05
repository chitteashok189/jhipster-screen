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

describe('EnnumerationType e2e test', () => {
  const ennumerationTypePageUrl = '/ennumeration-type';
  const ennumerationTypePageUrlPattern = new RegExp('/ennumeration-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ennumerationTypeSample = {};

  let ennumerationType: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ennumeration-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ennumeration-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ennumeration-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ennumerationType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ennumeration-types/${ennumerationType.id}`,
      }).then(() => {
        ennumerationType = undefined;
      });
    }
  });

  it('EnnumerationTypes menu should load EnnumerationTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ennumeration-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EnnumerationType').should('exist');
    cy.url().should('match', ennumerationTypePageUrlPattern);
  });

  describe('EnnumerationType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ennumerationTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EnnumerationType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ennumeration-type/new$'));
        cy.getEntityCreateUpdateHeading('EnnumerationType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ennumeration-types',
          body: ennumerationTypeSample,
        }).then(({ body }) => {
          ennumerationType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ennumeration-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [ennumerationType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(ennumerationTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EnnumerationType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ennumerationType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationTypePageUrlPattern);
      });

      it('edit button click should load edit EnnumerationType page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EnnumerationType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationTypePageUrlPattern);
      });

      it('last delete button click should delete instance of EnnumerationType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('ennumerationType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ennumerationTypePageUrlPattern);

        ennumerationType = undefined;
      });
    });
  });

  describe('new EnnumerationType page', () => {
    beforeEach(() => {
      cy.visit(`${ennumerationTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EnnumerationType');
    });

    it('should create an instance of EnnumerationType', () => {
      cy.get(`[data-cy="ennumerationType"]`).type('45290').should('have.value', '45290');

      cy.get(`[data-cy="hasTable"]`).should('not.be.checked');
      cy.get(`[data-cy="hasTable"]`).click().should('be.checked');

      cy.get(`[data-cy="description"]`).type('Internal').should('have.value', 'Internal');

      cy.get(`[data-cy="ennumeration"]`).type('communities world-class invoice').should('have.value', 'communities world-class invoice');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        ennumerationType = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', ennumerationTypePageUrlPattern);
    });
  });
});
