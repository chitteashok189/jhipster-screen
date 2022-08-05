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

describe('Breeder e2e test', () => {
  const breederPageUrl = '/breeder';
  const breederPageUrlPattern = new RegExp('/breeder(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const breederSample = {};

  let breeder: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/breeders+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/breeders').as('postEntityRequest');
    cy.intercept('DELETE', '/api/breeders/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (breeder) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/breeders/${breeder.id}`,
      }).then(() => {
        breeder = undefined;
      });
    }
  });

  it('Breeders menu should load Breeders page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('breeder');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Breeder').should('exist');
    cy.url().should('match', breederPageUrlPattern);
  });

  describe('Breeder page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(breederPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Breeder page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/breeder/new$'));
        cy.getEntityCreateUpdateHeading('Breeder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', breederPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/breeders',
          body: breederSample,
        }).then(({ body }) => {
          breeder = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/breeders+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [breeder],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(breederPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Breeder page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('breeder');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', breederPageUrlPattern);
      });

      it('edit button click should load edit Breeder page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Breeder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', breederPageUrlPattern);
      });

      it('last delete button click should delete instance of Breeder', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('breeder').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', breederPageUrlPattern);

        breeder = undefined;
      });
    });
  });

  describe('new Breeder page', () => {
    beforeEach(() => {
      cy.visit(`${breederPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Breeder');
    });

    it('should create an instance of Breeder', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('64b4b53a-08ae-4ece-9c33-cc695d9c80c4')
        .invoke('val')
        .should('match', new RegExp('64b4b53a-08ae-4ece-9c33-cc695d9c80c4'));

      cy.get(`[data-cy="breederName"]`).type('Refined District Locks').should('have.value', 'Refined District Locks');

      cy.get(`[data-cy="breederType"]`).type('79652').should('have.value', '79652');

      cy.get(`[data-cy="breederStatus"]`).type('58993').should('have.value', '58993');

      cy.get(`[data-cy="breederDescription"]`).type('Account Realigned Borders').should('have.value', 'Account Realigned Borders');

      cy.get(`[data-cy="createdBy"]`).type('63197').should('have.value', '63197');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T17:49').should('have.value', '2022-08-02T17:49');

      cy.get(`[data-cy="updatedBy"]`).type('56959').should('have.value', '56959');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T15:33').should('have.value', '2022-08-02T15:33');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        breeder = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', breederPageUrlPattern);
    });
  });
});
