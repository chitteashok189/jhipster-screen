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

describe('PartyNote e2e test', () => {
  const partyNotePageUrl = '/party-note';
  const partyNotePageUrlPattern = new RegExp('/party-note(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const partyNoteSample = {};

  let partyNote: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/party-notes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/party-notes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/party-notes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (partyNote) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/party-notes/${partyNote.id}`,
      }).then(() => {
        partyNote = undefined;
      });
    }
  });

  it('PartyNotes menu should load PartyNotes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('party-note');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PartyNote').should('exist');
    cy.url().should('match', partyNotePageUrlPattern);
  });

  describe('PartyNote page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(partyNotePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PartyNote page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/party-note/new$'));
        cy.getEntityCreateUpdateHeading('PartyNote');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyNotePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/party-notes',
          body: partyNoteSample,
        }).then(({ body }) => {
          partyNote = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/party-notes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [partyNote],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(partyNotePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PartyNote page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('partyNote');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyNotePageUrlPattern);
      });

      it('edit button click should load edit PartyNote page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PartyNote');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyNotePageUrlPattern);
      });

      it('last delete button click should delete instance of PartyNote', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('partyNote').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', partyNotePageUrlPattern);

        partyNote = undefined;
      });
    });
  });

  describe('new PartyNote page', () => {
    beforeEach(() => {
      cy.visit(`${partyNotePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PartyNote');
    });

    it('should create an instance of PartyNote', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('3d5b68aa-686f-4382-9e4b-4954bf0fce28')
        .invoke('val')
        .should('match', new RegExp('3d5b68aa-686f-4382-9e4b-4954bf0fce28'));

      cy.get(`[data-cy="noteID"]`).type('8420').should('have.value', '8420');

      cy.get(`[data-cy="createdBy"]`).type('30035').should('have.value', '30035');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T23:27').should('have.value', '2022-08-02T23:27');

      cy.get(`[data-cy="updatedBy"]`).type('98717').should('have.value', '98717');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T05:44').should('have.value', '2022-08-03T05:44');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        partyNote = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', partyNotePageUrlPattern);
    });
  });
});
