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

describe('RoleType e2e test', () => {
  const roleTypePageUrl = '/role-type';
  const roleTypePageUrlPattern = new RegExp('/role-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const roleTypeSample = {};

  let roleType: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/role-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/role-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/role-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (roleType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/role-types/${roleType.id}`,
      }).then(() => {
        roleType = undefined;
      });
    }
  });

  it('RoleTypes menu should load RoleTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('role-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RoleType').should('exist');
    cy.url().should('match', roleTypePageUrlPattern);
  });

  describe('RoleType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(roleTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RoleType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/role-type/new$'));
        cy.getEntityCreateUpdateHeading('RoleType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/role-types',
          body: roleTypeSample,
        }).then(({ body }) => {
          roleType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/role-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [roleType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(roleTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RoleType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('roleType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypePageUrlPattern);
      });

      it('edit button click should load edit RoleType page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RoleType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypePageUrlPattern);
      });

      it('last delete button click should delete instance of RoleType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('roleType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypePageUrlPattern);

        roleType = undefined;
      });
    });
  });

  describe('new RoleType page', () => {
    beforeEach(() => {
      cy.visit(`${roleTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RoleType');
    });

    it('should create an instance of RoleType', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('4adddbdd-c3dd-4dcd-b2e4-5b3509243d42')
        .invoke('val')
        .should('match', new RegExp('4adddbdd-c3dd-4dcd-b2e4-5b3509243d42'));

      cy.get(`[data-cy="hasTable"]`).should('not.be.checked');
      cy.get(`[data-cy="hasTable"]`).click().should('be.checked');

      cy.get(`[data-cy="description"]`).type('copying synthesizing Plastic').should('have.value', 'copying synthesizing Plastic');

      cy.get(`[data-cy="childRoleType"]`).type('85686').should('have.value', '85686');

      cy.get(`[data-cy="validPartyRelationshipType"]`).type('67739').should('have.value', '67739');

      cy.get(`[data-cy="validFromPartyRelationshipType"]`).type('20470').should('have.value', '20470');

      cy.get(`[data-cy="partyInvitationRoleAssociation"]`).type('Egyptian').should('have.value', 'Egyptian');

      cy.get(`[data-cy="createdBy"]`).type('80901').should('have.value', '80901');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T21:05').should('have.value', '2022-08-02T21:05');

      cy.get(`[data-cy="updatedBy"]`).type('57101').should('have.value', '57101');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T11:22').should('have.value', '2022-08-02T11:22');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        roleType = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', roleTypePageUrlPattern);
    });
  });
});
