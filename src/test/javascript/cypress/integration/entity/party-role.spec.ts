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

describe('PartyRole e2e test', () => {
  const partyRolePageUrl = '/party-role';
  const partyRolePageUrlPattern = new RegExp('/party-role(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyRoleSample = {};

  let partyRole: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-roles+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-roles').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-roles/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyRole) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-roles/${partyRole.id}`,
      }).then(() => {
        partyRole = undefined;
      });
    }
  });

  it('PartyRoles menu should load PartyRoles page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-role');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyRole').should('exist');
    cy.url().should('match', partyRolePageUrlPattern);
  });

  describe('PartyRole page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyRolePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyRole page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-role/new$'));
        cy.getEntityCreateUpdateHeading('PartyRole');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRolePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-roles',
          body: partyRoleSample,
        }).then(({ body }) => {
          partyRole = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-roles+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyRole],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyRolePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyRole page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyRole');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRolePageUrlPattern);
      });

      it('edit button click should load edit PartyRole page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyRole');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRolePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyRole', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyRole').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRolePageUrlPattern);

        partyRole = undefined;
      });
    });
  });

  describe('new PartyRole page', () => {
    beforeEach(() => {
      cy.visit(`${partyRolePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyRole');
    });

    it('should create an instance of PartyRole', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('53a05839-ee79-4739-8b52-60b1b9738c2c')
        .invoke('val')
        .should('match', new RegExp('53a05839-ee79-4739-8b52-60b1b9738c2c'));

      cy.get(`[data-cy="fromAgreement"]`).type('override').should('have.value', 'override');

      cy.get(`[data-cy="toAgreement"]`).type('Bedfordshire').should('have.value', 'Bedfordshire');

      cy.get(`[data-cy="fromCommunicationEvent"]`).type('deposit target').should('have.value', 'deposit target');

      cy.get(`[data-cy="toCommunicationEvent"]`).type('Borders services').should('have.value', 'Borders services');

      cy.get(`[data-cy="partyIdFrom"]`).type('59012').should('have.value', '59012');

      cy.get(`[data-cy="partyIdTO"]`).type('67912').should('have.value', '67912');

      cy.get(`[data-cy="createdBy"]`).type('72626').should('have.value', '72626');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T07:51').should('have.value', '2022-08-03T07:51');

      cy.get(`[data-cy="updatedBy"]`).type('70820').should('have.value', '70820');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T03:15').should('have.value', '2022-08-03T03:15');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyRole = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyRolePageUrlPattern);
    });
  });
});
