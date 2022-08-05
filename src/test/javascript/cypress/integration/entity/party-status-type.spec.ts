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

describe('PartyStatusType e2e test', () => {
  const partyStatusTypePageUrl = '/party-status-type';
  const partyStatusTypePageUrlPattern = new RegExp('/party-status-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyStatusTypeSample = {};

  let partyStatusType: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-status-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-status-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-status-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyStatusType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-status-types/${partyStatusType.id}`,
      }).then(() => {
        partyStatusType = undefined;
      });
    }
  });

  it('PartyStatusTypes menu should load PartyStatusTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-status-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyStatusType').should('exist');
    cy.url().should('match', partyStatusTypePageUrlPattern);
  });

  describe('PartyStatusType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyStatusTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyStatusType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-status-type/new$'));
        cy.getEntityCreateUpdateHeading('PartyStatusType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-status-types',
          body: partyStatusTypeSample,
        }).then(({ body }) => {
          partyStatusType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-status-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyStatusType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyStatusTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyStatusType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyStatusType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusTypePageUrlPattern);
      });

      it('edit button click should load edit PartyStatusType page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyStatusType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusTypePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyStatusType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyStatusType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyStatusTypePageUrlPattern);

        partyStatusType = undefined;
      });
    });
  });

  describe('new PartyStatusType page', () => {
    beforeEach(() => {
      cy.visit(`${partyStatusTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyStatusType');
    });

    it('should create an instance of PartyStatusType', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('beb398a0-c4f3-4e33-bd75-2380c77f5010')
        .invoke('val')
        .should('match', new RegExp('beb398a0-c4f3-4e33-bd75-2380c77f5010'));

      cy.get(`[data-cy="parentTypeID"]`).type('58607').should('have.value', '58607');

      cy.get(`[data-cy="hasTable"]`).should('not.be.checked');
      cy.get(`[data-cy="hasTable"]`).click().should('be.checked');

      cy.get(`[data-cy="description"]`).type('online leading-edge ivory').should('have.value', 'online leading-edge ivory');

      cy.get(`[data-cy="childStatusType"]`).type('27780').should('have.value', '27780');

      cy.get(`[data-cy="createdBy"]`).type('87746').should('have.value', '87746');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T00:40').should('have.value', '2022-08-03T00:40');

      cy.get(`[data-cy="updatedBy"]`).type('95338').should('have.value', '95338');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T15:59').should('have.value', '2022-08-02T15:59');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyStatusType = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyStatusTypePageUrlPattern);
    });
  });
});
