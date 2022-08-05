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

describe('ApplicationUserSecurityGroup e2e test', () => {
  const applicationUserSecurityGroupPageUrl = '/application-user-security-group';
  const applicationUserSecurityGroupPageUrlPattern = new RegExp('/application-user-security-group(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const applicationUserSecurityGroupSample = {};

  let applicationUserSecurityGroup: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/application-user-security-groups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/application-user-security-groups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/application-user-security-groups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (applicationUserSecurityGroup) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/application-user-security-groups/${applicationUserSecurityGroup.id}`,
      }).then(() => {
        applicationUserSecurityGroup = undefined;
      });
    }
  });

  it('ApplicationUserSecurityGroups menu should load ApplicationUserSecurityGroups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('application-user-security-group');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ApplicationUserSecurityGroup').should('exist');
    cy.url().should('match', applicationUserSecurityGroupPageUrlPattern);
  });

  describe('ApplicationUserSecurityGroup page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(applicationUserSecurityGroupPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ApplicationUserSecurityGroup page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/application-user-security-group/new$'));
        cy.getEntityCreateUpdateHeading('ApplicationUserSecurityGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserSecurityGroupPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/application-user-security-groups',
          body: applicationUserSecurityGroupSample,
        }).then(({ body }) => {
          applicationUserSecurityGroup = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/application-user-security-groups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [applicationUserSecurityGroup],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(applicationUserSecurityGroupPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ApplicationUserSecurityGroup page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('applicationUserSecurityGroup');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserSecurityGroupPageUrlPattern);
      });

      it('edit button click should load edit ApplicationUserSecurityGroup page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ApplicationUserSecurityGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserSecurityGroupPageUrlPattern);
      });

      it('last delete button click should delete instance of ApplicationUserSecurityGroup', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('applicationUserSecurityGroup').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserSecurityGroupPageUrlPattern);

        applicationUserSecurityGroup = undefined;
      });
    });
  });

  describe('new ApplicationUserSecurityGroup page', () => {
    beforeEach(() => {
      cy.visit(`${applicationUserSecurityGroupPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ApplicationUserSecurityGroup');
    });

    it('should create an instance of ApplicationUserSecurityGroup', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('ad6f96fc-aeae-48bd-b19b-e0202ad434dd')
        .invoke('val')
        .should('match', new RegExp('ad6f96fc-aeae-48bd-b19b-e0202ad434dd'));

      cy.get(`[data-cy="fromDate"]`).type('2022-08-02T12:42').should('have.value', '2022-08-02T12:42');

      cy.get(`[data-cy="thruDate"]`).type('2022-08-02T09:06').should('have.value', '2022-08-02T09:06');

      cy.get(`[data-cy="createdBy"]`).type('89990').should('have.value', '89990');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T14:30').should('have.value', '2022-08-02T14:30');

      cy.get(`[data-cy="updatedBy"]`).type('12846').should('have.value', '12846');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T09:27').should('have.value', '2022-08-02T09:27');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        applicationUserSecurityGroup = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', applicationUserSecurityGroupPageUrlPattern);
    });
  });
});
