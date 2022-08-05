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

describe('PartyType e2e test', () => {
  const partyTypePageUrl = '/party-type';
  const partyTypePageUrlPattern = new RegExp('/party-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyTypeSample = {};

  let partyType: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-types/${partyType.id}`,
      }).then(() => {
        partyType = undefined;
      });
    }
  });

  it('PartyTypes menu should load PartyTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyType').should('exist');
    cy.url().should('match', partyTypePageUrlPattern);
  });

  describe('PartyType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-type/new$'));
        cy.getEntityCreateUpdateHeading('PartyType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-types',
          body: partyTypeSample,
        }).then(({ body }) => {
          partyType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypePageUrlPattern);
      });

      it('edit button click should load edit PartyType page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyTypePageUrlPattern);

        partyType = undefined;
      });
    });
  });

  describe('new PartyType page', () => {
    beforeEach(() => {
      cy.visit(`${partyTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyType');
    });

    it('should create an instance of PartyType', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('cf7e703d-c8b6-4a28-8563-9f68df3d7427')
        .invoke('val')
        .should('match', new RegExp('cf7e703d-c8b6-4a28-8563-9f68df3d7427'));

      cy.get(`[data-cy="parentTypeID"]`).select('Group');

      cy.get(`[data-cy="hasTable"]`).type('Automated withdrawal Card').should('have.value', 'Automated withdrawal Card');

      cy.get(`[data-cy="description"]`).type('Monitored invoice').should('have.value', 'Monitored invoice');

      cy.get(`[data-cy="partyTypeAttr"]`).type('Infrastructure').should('have.value', 'Infrastructure');

      cy.get(`[data-cy="createdBy"]`).type('38458').should('have.value', '38458');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T10:04').should('have.value', '2022-08-02T10:04');

      cy.get(`[data-cy="updatedBy"]`).type('40287').should('have.value', '40287');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T14:34').should('have.value', '2022-08-02T14:34');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyType = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyTypePageUrlPattern);
    });
  });
});
