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

describe('SecurityGroup e2e test', () => {
  const securityGroupPageUrl = '/security-group';
  const securityGroupPageUrlPattern = new RegExp('/security-group(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const securityGroupSample = {};

  let securityGroup: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/security-groups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/security-groups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/security-groups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (securityGroup) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/security-groups/${securityGroup.id}`,
      }).then(() => {
        securityGroup = undefined;
      });
    }
  });

  it('SecurityGroups menu should load SecurityGroups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('security-group');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SecurityGroup').should('exist');
    cy.url().should('match', securityGroupPageUrlPattern);
  });

  describe('SecurityGroup page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(securityGroupPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SecurityGroup page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/security-group/new$'));
        cy.getEntityCreateUpdateHeading('SecurityGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/security-groups',
          body: securityGroupSample,
        }).then(({ body }) => {
          securityGroup = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/security-groups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [securityGroup],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(securityGroupPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SecurityGroup page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('securityGroup');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPageUrlPattern);
      });

      it('edit button click should load edit SecurityGroup page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SecurityGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPageUrlPattern);
      });

      it('last delete button click should delete instance of SecurityGroup', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('securityGroup').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', securityGroupPageUrlPattern);

        securityGroup = undefined;
      });
    });
  });

  describe('new SecurityGroup page', () => {
    beforeEach(() => {
      cy.visit(`${securityGroupPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SecurityGroup');
    });

    it('should create an instance of SecurityGroup', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('e4b8340d-9999-4364-9a43-8519a5c3f031')
        .invoke('val')
        .should('match', new RegExp('e4b8340d-9999-4364-9a43-8519a5c3f031'));

      cy.get(`[data-cy="description"]`).type('disintermediate Salad').should('have.value', 'disintermediate Salad');

      cy.get(`[data-cy="createdBy"]`).type('20401').should('have.value', '20401');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T10:15').should('have.value', '2022-08-02T10:15');

      cy.get(`[data-cy="updatedBy"]`).type('28928').should('have.value', '28928');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T05:35').should('have.value', '2022-08-03T05:35');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        securityGroup = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', securityGroupPageUrlPattern);
    });
  });
});
