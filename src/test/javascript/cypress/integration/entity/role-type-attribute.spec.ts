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

describe('RoleTypeAttribute e2e test', () => {
  const roleTypeAttributePageUrl = '/role-type-attribute';
  const roleTypeAttributePageUrlPattern = new RegExp('/role-type-attribute(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const roleTypeAttributeSample = {};

  let roleTypeAttribute: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/role-type-attributes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/role-type-attributes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/role-type-attributes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (roleTypeAttribute) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/role-type-attributes/${roleTypeAttribute.id}`,
      }).then(() => {
        roleTypeAttribute = undefined;
      });
    }
  });

  it('RoleTypeAttributes menu should load RoleTypeAttributes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('role-type-attribute');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RoleTypeAttribute').should('exist');
    cy.url().should('match', roleTypeAttributePageUrlPattern);
  });

  describe('RoleTypeAttribute page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(roleTypeAttributePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RoleTypeAttribute page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/role-type-attribute/new$'));
        cy.getEntityCreateUpdateHeading('RoleTypeAttribute');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypeAttributePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/role-type-attributes',
          body: roleTypeAttributeSample,
        }).then(({ body }) => {
          roleTypeAttribute = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/role-type-attributes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [roleTypeAttribute],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(roleTypeAttributePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RoleTypeAttribute page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('roleTypeAttribute');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypeAttributePageUrlPattern);
      });

      it('edit button click should load edit RoleTypeAttribute page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RoleTypeAttribute');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypeAttributePageUrlPattern);
      });

      it('last delete button click should delete instance of RoleTypeAttribute', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('roleTypeAttribute').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', roleTypeAttributePageUrlPattern);

        roleTypeAttribute = undefined;
      });
    });
  });

  describe('new RoleTypeAttribute page', () => {
    beforeEach(() => {
      cy.visit(`${roleTypeAttributePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RoleTypeAttribute');
    });

    it('should create an instance of RoleTypeAttribute', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('64595b4f-9169-4335-9eae-3fac880f5156')
        .invoke('val')
        .should('match', new RegExp('64595b4f-9169-4335-9eae-3fac880f5156'));

      cy.get(`[data-cy="attributeName"]`).type('Berkshire').should('have.value', 'Berkshire');

      cy.get(`[data-cy="description"]`).type('streamline Account User-centric').should('have.value', 'streamline Account User-centric');

      cy.get(`[data-cy="createdBy"]`).type('75003').should('have.value', '75003');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T11:52').should('have.value', '2022-08-02T11:52');

      cy.get(`[data-cy="updatedBy"]`).type('89111').should('have.value', '89111');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T07:52').should('have.value', '2022-08-03T07:52');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        roleTypeAttribute = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', roleTypeAttributePageUrlPattern);
    });
  });
});
