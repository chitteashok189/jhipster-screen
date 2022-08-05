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

describe('SecurityGroupPermission e2e test', () => {
  const securityGroupPermissionPageUrl = '/security-group-permission';
  const securityGroupPermissionPageUrlPattern = new RegExp('/security-group-permission(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const securityGroupPermissionSample = {};

  let securityGroupPermission: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/security-group-permissions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/security-group-permissions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/security-group-permissions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (securityGroupPermission) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/security-group-permissions/${securityGroupPermission.id}`,
      }).then(() => {
        securityGroupPermission = undefined;
      });
    }
  });

  it('SecurityGroupPermissions menu should load SecurityGroupPermissions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('security-group-permission');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SecurityGroupPermission').should('exist');
    cy.url().should('match', securityGroupPermissionPageUrlPattern);
  });

  describe('SecurityGroupPermission page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(securityGroupPermissionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SecurityGroupPermission page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/security-group-permission/new$'));
        cy.getEntityCreateUpdateHeading('SecurityGroupPermission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPermissionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/security-group-permissions',
          body: securityGroupPermissionSample,
        }).then(({ body }) => {
          securityGroupPermission = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/security-group-permissions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [securityGroupPermission],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(securityGroupPermissionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SecurityGroupPermission page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('securityGroupPermission');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPermissionPageUrlPattern);
      });

      it('edit button click should load edit SecurityGroupPermission page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SecurityGroupPermission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPermissionPageUrlPattern);
      });

      it('last delete button click should delete instance of SecurityGroupPermission', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('securityGroupPermission').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPermissionPageUrlPattern);

        securityGroupPermission = undefined;
      });
    });
  });

  describe('new SecurityGroupPermission page', () => {
    beforeEach(() => {
      cy.visit(`${securityGroupPermissionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SecurityGroupPermission');
    });

    it('should create an instance of SecurityGroupPermission', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('e345c524-f095-4f19-91a6-2a540c0e83cd')
        .invoke('val')
        .should('match', new RegExp('e345c524-f095-4f19-91a6-2a540c0e83cd'));

      cy.get(`[data-cy="createdBy"]`).type('94922').should('have.value', '94922');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T04:00').should('have.value', '2022-08-03T04:00');

      cy.get(`[data-cy="updatedBy"]`).type('71819').should('have.value', '71819');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T06:03').should('have.value', '2022-08-03T06:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        securityGroupPermission = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', securityGroupPermissionPageUrlPattern);
    });
  });
});
