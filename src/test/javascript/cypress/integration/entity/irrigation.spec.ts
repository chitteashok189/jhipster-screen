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

describe('Irrigation e2e test', () => {
  const irrigationPageUrl = '/irrigation';
  const irrigationPageUrlPattern = new RegExp('/irrigation(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const irrigationSample = {};

  let irrigation: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/irrigations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/irrigations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/irrigations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (irrigation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/irrigations/${irrigation.id}`,
      }).then(() => {
        irrigation = undefined;
      });
    }
  });

  it('Irrigations menu should load Irrigations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('irrigation');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Irrigation').should('exist');
    cy.url().should('match', irrigationPageUrlPattern);
  });

  describe('Irrigation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(irrigationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Irrigation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/irrigation/new$'));
        cy.getEntityCreateUpdateHeading('Irrigation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', irrigationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/irrigations',
          body: irrigationSample,
        }).then(({ body }) => {
          irrigation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/irrigations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [irrigation],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(irrigationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Irrigation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('irrigation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', irrigationPageUrlPattern);
      });

      it('edit button click should load edit Irrigation page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Irrigation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', irrigationPageUrlPattern);
      });

      it('last delete button click should delete instance of Irrigation', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('irrigation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', irrigationPageUrlPattern);

        irrigation = undefined;
      });
    });
  });

  describe('new Irrigation page', () => {
    beforeEach(() => {
      cy.visit(`${irrigationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Irrigation');
    });

    it('should create an instance of Irrigation', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('5340e0b9-0adb-4abe-bb90-09ea5f140ce9')
        .invoke('val')
        .should('match', new RegExp('5340e0b9-0adb-4abe-bb90-09ea5f140ce9'));

      cy.get(`[data-cy="source"]`).select('Automatic');

      cy.get(`[data-cy="nutrientLevel"]`).type('66720').should('have.value', '66720');

      cy.get(`[data-cy="solarRadiation"]`).type('88187').should('have.value', '88187');

      cy.get(`[data-cy="inletFlow"]`).type('38124').should('have.value', '38124');

      cy.get(`[data-cy="outletFlow"]`).type('36078').should('have.value', '36078');

      cy.get(`[data-cy="createdBy"]`).type('44925').should('have.value', '44925');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T21:13').should('have.value', '2022-08-02T21:13');

      cy.get(`[data-cy="updatedBy"]`).type('62937').should('have.value', '62937');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T07:52').should('have.value', '2022-08-03T07:52');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        irrigation = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', irrigationPageUrlPattern);
    });
  });
});
