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

describe('Disorder e2e test', () => {
  const disorderPageUrl = '/disorder';
  const disorderPageUrlPattern = new RegExp('/disorder(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const disorderSample = {};

  let disorder: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/disorders+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/disorders').as('postEntityRequest');
    cy.intercept('DELETE', '/api/disorders/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (disorder) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/disorders/${disorder.id}`,
      }).then(() => {
        disorder = undefined;
      });
    }
  });

  it('Disorders menu should load Disorders page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('disorder');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Disorder').should('exist');
    cy.url().should('match', disorderPageUrlPattern);
  });

  describe('Disorder page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(disorderPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Disorder page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/disorder/new$'));
        cy.getEntityCreateUpdateHeading('Disorder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', disorderPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/disorders',
          body: disorderSample,
        }).then(({ body }) => {
          disorder = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/disorders+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [disorder],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(disorderPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Disorder page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('disorder');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', disorderPageUrlPattern);
      });

      it('edit button click should load edit Disorder page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Disorder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', disorderPageUrlPattern);
      });

      it('last delete button click should delete instance of Disorder', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('disorder').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', disorderPageUrlPattern);

        disorder = undefined;
      });
    });
  });

  describe('new Disorder page', () => {
    beforeEach(() => {
      cy.visit(`${disorderPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Disorder');
    });

    it('should create an instance of Disorder', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('81a0f7f3-ab23-491f-af15-fa664571ef6b')
        .invoke('val')
        .should('match', new RegExp('81a0f7f3-ab23-491f-af15-fa664571ef6b'));

      cy.get(`[data-cy="physiologicalDisorder"]`).type('sensor Islands Refined').should('have.value', 'sensor Islands Refined');

      cy.get(`[data-cy="cause"]`).type('Fiji scale fuchsia').should('have.value', 'Fiji scale fuchsia');

      cy.get(`[data-cy="controlMeasure"]`).type('high-level Lead').should('have.value', 'high-level Lead');

      cy.get(`[data-cy="createdBy"]`).type('40212').should('have.value', '40212');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T13:25').should('have.value', '2022-08-02T13:25');

      cy.get(`[data-cy="updatedBy"]`).type('33463').should('have.value', '33463');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T09:36').should('have.value', '2022-08-02T09:36');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        disorder = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', disorderPageUrlPattern);
    });
  });
});
