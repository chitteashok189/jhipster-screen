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

describe('Scouting e2e test', () => {
  const scoutingPageUrl = '/scouting';
  const scoutingPageUrlPattern = new RegExp('/scouting(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const scoutingSample = {};

  let scouting: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/scoutings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/scoutings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/scoutings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (scouting) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/scoutings/${scouting.id}`,
      }).then(() => {
        scouting = undefined;
      });
    }
  });

  it('Scoutings menu should load Scoutings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('scouting');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Scouting').should('exist');
    cy.url().should('match', scoutingPageUrlPattern);
  });

  describe('Scouting page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(scoutingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Scouting page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/scouting/new$'));
        cy.getEntityCreateUpdateHeading('Scouting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', scoutingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/scoutings',
          body: scoutingSample,
        }).then(({ body }) => {
          scouting = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/scoutings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [scouting],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(scoutingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Scouting page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('scouting');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', scoutingPageUrlPattern);
      });

      it('edit button click should load edit Scouting page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Scouting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', scoutingPageUrlPattern);
      });

      it('last delete button click should delete instance of Scouting', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('scouting').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', scoutingPageUrlPattern);

        scouting = undefined;
      });
    });
  });

  describe('new Scouting page', () => {
    beforeEach(() => {
      cy.visit(`${scoutingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Scouting');
    });

    it('should create an instance of Scouting', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('0541a454-9487-4a9f-9162-bad6b9c230f3')
        .invoke('val')
        .should('match', new RegExp('0541a454-9487-4a9f-9162-bad6b9c230f3'));

      cy.get(`[data-cy="scoutingDate"]`).type('2022-08-03T06:44').should('have.value', '2022-08-03T06:44');

      cy.get(`[data-cy="scout"]`).type('Mississippi functionalities').should('have.value', 'Mississippi functionalities');

      cy.get(`[data-cy="scoutingType"]`).select('Others');

      cy.get(`[data-cy="scoutingCoordinates"]`).type('24721').should('have.value', '24721');

      cy.get(`[data-cy="scoutingArea"]`).type('89552').should('have.value', '89552');

      cy.get(`[data-cy="cropState"]`).select('Normal');

      cy.get(`[data-cy="comments"]`).type('Mississippi').should('have.value', 'Mississippi');

      cy.get(`[data-cy="createdBy"]`).type('42107').should('have.value', '42107');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T21:46').should('have.value', '2022-08-02T21:46');

      cy.get(`[data-cy="updatedBy"]`).type('8074').should('have.value', '8074');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T04:56').should('have.value', '2022-08-03T04:56');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        scouting = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', scoutingPageUrlPattern);
    });
  });
});
