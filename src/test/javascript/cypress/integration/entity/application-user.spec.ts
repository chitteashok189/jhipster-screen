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

describe('ApplicationUser e2e test', () => {
  const applicationUserPageUrl = '/application-user';
  const applicationUserPageUrlPattern = new RegExp('/application-user(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const applicationUserSample = {};

  let applicationUser: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/application-users+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/application-users').as('postEntityRequest');
    cy.intercept('DELETE', '/api/application-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (applicationUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/application-users/${applicationUser.id}`,
      }).then(() => {
        applicationUser = undefined;
      });
    }
  });

  it('ApplicationUsers menu should load ApplicationUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('application-user');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ApplicationUser').should('exist');
    cy.url().should('match', applicationUserPageUrlPattern);
  });

  describe('ApplicationUser page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(applicationUserPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ApplicationUser page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/application-user/new$'));
        cy.getEntityCreateUpdateHeading('ApplicationUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/application-users',
          body: applicationUserSample,
        }).then(({ body }) => {
          applicationUser = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/application-users+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [applicationUser],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(applicationUserPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ApplicationUser page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('applicationUser');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserPageUrlPattern);
      });

      it('edit button click should load edit ApplicationUser page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ApplicationUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserPageUrlPattern);
      });

      it('last delete button click should delete instance of ApplicationUser', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('applicationUser').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', applicationUserPageUrlPattern);

        applicationUser = undefined;
      });
    });
  });

  describe('new ApplicationUser page', () => {
    beforeEach(() => {
      cy.visit(`${applicationUserPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ApplicationUser');
    });

    it('should create an instance of ApplicationUser', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('e7662229-4b8f-4a86-9d22-793d97b57df9')
        .invoke('val')
        .should('match', new RegExp('e7662229-4b8f-4a86-9d22-793d97b57df9'));

      cy.get(`[data-cy="currentPassword"]`).type('PCI').should('have.value', 'PCI');

      cy.get(`[data-cy="passwordHint"]`).type('Group').should('have.value', 'Group');

      cy.get(`[data-cy="isSystemEnables"]`).should('not.be.checked');
      cy.get(`[data-cy="isSystemEnables"]`).click().should('be.checked');

      cy.get(`[data-cy="hasLoggedOut"]`).should('not.be.checked');
      cy.get(`[data-cy="hasLoggedOut"]`).click().should('be.checked');

      cy.get(`[data-cy="requirePasswordChange"]`).should('not.be.checked');
      cy.get(`[data-cy="requirePasswordChange"]`).click().should('be.checked');

      cy.get(`[data-cy="lastCurrencyUom"]`).type('1447').should('have.value', '1447');

      cy.get(`[data-cy="lastLocale"]`).type('22926').should('have.value', '22926');

      cy.get(`[data-cy="lastTimeZone"]`).type('92276').should('have.value', '92276');

      cy.get(`[data-cy="disabledDateTime"]`).type('2022-08-03T01:09').should('have.value', '2022-08-03T01:09');

      cy.get(`[data-cy="successiveFailedLogins"]`).type('24414').should('have.value', '24414');

      cy.get(`[data-cy="applicationUserSecurityGroup"]`).type('fuchsia').should('have.value', 'fuchsia');

      cy.get(`[data-cy="createdBy"]`).type('32993').should('have.value', '32993');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T16:28').should('have.value', '2022-08-02T16:28');

      cy.get(`[data-cy="updatedBy"]`).type('72610').should('have.value', '72610');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T12:21').should('have.value', '2022-08-02T12:21');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        applicationUser = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', applicationUserPageUrlPattern);
    });
  });
});
