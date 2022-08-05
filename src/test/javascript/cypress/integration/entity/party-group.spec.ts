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

describe('PartyGroup e2e test', () => {
  const partyGroupPageUrl = '/party-group';
  const partyGroupPageUrlPattern = new RegExp('/party-group(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyGroupSample = {};

  let partyGroup: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-groups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-groups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-groups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyGroup) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-groups/${partyGroup.id}`,
      }).then(() => {
        partyGroup = undefined;
      });
    }
  });

  it('PartyGroups menu should load PartyGroups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-group');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyGroup').should('exist');
    cy.url().should('match', partyGroupPageUrlPattern);
  });

  describe('PartyGroup page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyGroupPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyGroup page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-group/new$'));
        cy.getEntityCreateUpdateHeading('PartyGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyGroupPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-groups',
          body: partyGroupSample,
        }).then(({ body }) => {
          partyGroup = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-groups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyGroup],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyGroupPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyGroup page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyGroup');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyGroupPageUrlPattern);
      });

      it('edit button click should load edit PartyGroup page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyGroupPageUrlPattern);
      });

      it('last delete button click should delete instance of PartyGroup', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyGroup').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyGroupPageUrlPattern);

        partyGroup = undefined;
      });
    });
  });

  describe('new PartyGroup page', () => {
    beforeEach(() => {
      cy.visit(`${partyGroupPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyGroup');
    });

    it('should create an instance of PartyGroup', () => {
      cy.get(`[data-cy="gUID"]`).type('116').should('have.value', '116');

      cy.get(`[data-cy="groupName"]`).type('Bacon').should('have.value', 'Bacon');

      cy.get(`[data-cy="localGroupName"]`).type('Papua').should('have.value', 'Papua');

      cy.get(`[data-cy="officeSiteName"]`).type('Berkshire haptic Kenyan').should('have.value', 'Berkshire haptic Kenyan');

      cy.get(`[data-cy="comments"]`).type('methodologies synthesizing').should('have.value', 'methodologies synthesizing');

      cy.get(`[data-cy="logoImageUrl"]`).type('B2B').should('have.value', 'B2B');

      cy.get(`[data-cy="createdBy"]`).type('50182').should('have.value', '50182');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T04:22').should('have.value', '2022-08-03T04:22');

      cy.get(`[data-cy="updatedBy"]`).type('17456').should('have.value', '17456');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T20:25').should('have.value', '2022-08-02T20:25');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyGroup = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyGroupPageUrlPattern);
    });
  });
});
