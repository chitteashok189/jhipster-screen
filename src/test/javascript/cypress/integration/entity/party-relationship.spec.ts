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

describe('PartyRelationship e2e test', () => {
  const partyRelationshipPageUrl = '/party-relationship';
  const partyRelationshipPageUrlPattern = new RegExp('/party-relationship(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyRelationshipSample = {};

  let partyRelationship: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-relationships+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-relationships').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-relationships/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyRelationship) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-relationships/${partyRelationship.id}`,
      }).then(() => {
        partyRelationship = undefined;
      });
    }
  });

  it('PartyRelationships menu should load PartyRelationships page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-relationship');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyRelationship').should('exist');
    cy.url().should('match', partyRelationshipPageUrlPattern);
  });

  describe('PartyRelationship page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyRelationshipPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyRelationship page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-relationship/new$'));
        cy.getEntityCreateUpdateHeading('PartyRelationship');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-relationships',
          body: partyRelationshipSample,
        }).then(({ body }) => {
          partyRelationship = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-relationships+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyRelationship],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyRelationshipPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyRelationship page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyRelationship');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipPageUrlPattern);
      });

      it('edit button click should load edit PartyRelationship page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyRelationship');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipPageUrlPattern);
      });

      it('last delete button click should delete instance of PartyRelationship', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyRelationship').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyRelationshipPageUrlPattern);

        partyRelationship = undefined;
      });
    });
  });

  describe('new PartyRelationship page', () => {
    beforeEach(() => {
      cy.visit(`${partyRelationshipPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyRelationship');
    });

    it('should create an instance of PartyRelationship', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('bce6e5e7-bdd2-407e-8bed-6e555cf94353')
        .invoke('val')
        .should('match', new RegExp('bce6e5e7-bdd2-407e-8bed-6e555cf94353'));

      cy.get(`[data-cy="partyIdTo"]`).type('64811').should('have.value', '64811');

      cy.get(`[data-cy="partyIdFrom"]`).type('23163').should('have.value', '23163');

      cy.get(`[data-cy="roleTypeIdFrom"]`).type('60604').should('have.value', '60604');

      cy.get(`[data-cy="roleTypeIdTo"]`).type('60795').should('have.value', '60795');

      cy.get(`[data-cy="fromDate"]`).type('2022-08-02T20:21').should('have.value', '2022-08-02T20:21');

      cy.get(`[data-cy="thruDate"]`).type('2022-08-02T09:14').should('have.value', '2022-08-02T09:14');

      cy.get(`[data-cy="relationshipName"]`).type('Streets Account').should('have.value', 'Streets Account');

      cy.get(`[data-cy="positionTitle"]`).type('Vermont Clothing invoice').should('have.value', 'Vermont Clothing invoice');

      cy.get(`[data-cy="comments"]`).type('infrastructure Concrete').should('have.value', 'infrastructure Concrete');

      cy.get(`[data-cy="createdBy"]`).type('10962').should('have.value', '10962');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T09:47').should('have.value', '2022-08-02T09:47');

      cy.get(`[data-cy="updatedBy"]`).type('85530').should('have.value', '85530');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T20:22').should('have.value', '2022-08-02T20:22');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyRelationship = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyRelationshipPageUrlPattern);
    });
  });
});
