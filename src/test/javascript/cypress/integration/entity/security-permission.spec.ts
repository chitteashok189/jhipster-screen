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

describe('SecurityPermission e2e test', () => {
  const securityPermissionPageUrl = '/security-permission';
  const securityPermissionPageUrlPattern = new RegExp('/security-permission(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const securityPermissionSample = {};

  let securityPermission: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/security-permissions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/security-permissions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/security-permissions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (securityPermission) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/security-permissions/${securityPermission.id}`,
      }).then(() => {
        securityPermission = undefined;
      });
    }
  });

  it('SecurityPermissions menu should load SecurityPermissions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('security-permission');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SecurityPermission').should('exist');
    cy.url().should('match', securityPermissionPageUrlPattern);
  });

  describe('SecurityPermission page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(securityPermissionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SecurityPermission page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/security-permission/new$'));
        cy.getEntityCreateUpdateHeading('SecurityPermission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityPermissionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/security-permissions',
          body: securityPermissionSample,
        }).then(({ body }) => {
          securityPermission = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/security-permissions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [securityPermission],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(securityPermissionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SecurityPermission page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('securityPermission');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityPermissionPageUrlPattern);
      });

      it('edit button click should load edit SecurityPermission page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SecurityPermission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityPermissionPageUrlPattern);
      });

      it('last delete button click should delete instance of SecurityPermission', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('securityPermission').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityPermissionPageUrlPattern);

        securityPermission = undefined;
      });
    });
  });

  describe('new SecurityPermission page', () => {
    beforeEach(() => {
      cy.visit(`${securityPermissionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SecurityPermission');
    });

    it('should create an instance of SecurityPermission', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('a5c59dae-0eae-46de-b235-23ee5ae95916')
        .invoke('val')
        .should('match', new RegExp('a5c59dae-0eae-46de-b235-23ee5ae95916'));

      cy.get(`[data-cy="description"]`).type('Rustic').should('have.value', 'Rustic');

      cy.get(`[data-cy="createdBy"]`).type('27776').should('have.value', '27776');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T02:58').should('have.value', '2022-08-03T02:58');

      cy.get(`[data-cy="updatedBy"]`).type('26029').should('have.value', '26029');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T09:57').should('have.value', '2022-08-02T09:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        securityPermission = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', securityPermissionPageUrlPattern);
    });
  });
});
