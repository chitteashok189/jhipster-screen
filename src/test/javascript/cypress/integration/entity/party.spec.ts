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

describe('Party e2e test', () => {
  const partyPageUrl = '/party';
  const partyPageUrlPattern = new RegExp('/party(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partySample = {};

  let party: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/parties+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/parties').as('postEntityRequest');
    cy.intercept('DELETE', '/api/parties/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (party) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/parties/${party.id}`,
      }).then(() => {
        party = undefined;
      });
    }
  });

  it('Parties menu should load Parties page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Party').should('exist');
    cy.url().should('match', partyPageUrlPattern);
  });

  describe('Party page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Party page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party/new$'));
        cy.getEntityCreateUpdateHeading('Party');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/parties',
          body: partySample,
        }).then(({ body }) => {
          party = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/parties+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [party],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Party page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('party');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyPageUrlPattern);
      });

      it('edit button click should load edit Party page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Party');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyPageUrlPattern);
      });

      it('last delete button click should delete instance of Party', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('party').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyPageUrlPattern);

        party = undefined;
      });
    });
  });

  describe('new Party page', () => {
    beforeEach(() => {
      cy.visit(`${partyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Party');
    });

    it('should create an instance of Party', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('3007bdb8-5f6a-4f4f-8703-57e040ff4bf7')
        .invoke('val')
        .should('match', new RegExp('3007bdb8-5f6a-4f4f-8703-57e040ff4bf7'));

      cy.get(`[data-cy="partyName"]`).type('backing Grove').should('have.value', 'backing Grove');

      cy.get(`[data-cy="statusID"]`).should('not.be.checked');
      cy.get(`[data-cy="statusID"]`).click().should('be.checked');

      cy.get(`[data-cy="description"]`).type('payment wireless').should('have.value', 'payment wireless');

      cy.get(`[data-cy="externalID"]`).type('40129').should('have.value', '40129');

      cy.get(`[data-cy="createdBy"]`).type('9379').should('have.value', '9379');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T20:05').should('have.value', '2022-08-02T20:05');

      cy.get(`[data-cy="updatedBy"]`).type('87404').should('have.value', '87404');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T05:03').should('have.value', '2022-08-03T05:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        party = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyPageUrlPattern);
    });
  });
});
