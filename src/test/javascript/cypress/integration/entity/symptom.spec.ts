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

describe('Symptom e2e test', () => {
  const symptomPageUrl = '/symptom';
  const symptomPageUrlPattern = new RegExp('/symptom(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const symptomSample = {};

  let symptom: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/symptoms+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/symptoms').as('postEntityRequest');
    cy.intercept('DELETE', '/api/symptoms/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (symptom) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/symptoms/${symptom.id}`,
      }).then(() => {
        symptom = undefined;
      });
    }
  });

  it('Symptoms menu should load Symptoms page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Symptom').should('exist');
    cy.url().should('match', symptomPageUrlPattern);
  });

  describe('Symptom page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(symptomPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Symptom page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/symptom/new$'));
        cy.getEntityCreateUpdateHeading('Symptom');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', symptomPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/symptoms',
          body: symptomSample,
        }).then(({ body }) => {
          symptom = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/symptoms+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [symptom],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(symptomPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Symptom page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('symptom');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', symptomPageUrlPattern);
      });

      it('edit button click should load edit Symptom page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Symptom');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', symptomPageUrlPattern);
      });

      it('last delete button click should delete instance of Symptom', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('symptom').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', symptomPageUrlPattern);

        symptom = undefined;
      });
    });
  });

  describe('new Symptom page', () => {
    beforeEach(() => {
      cy.visit(`${symptomPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Symptom');
    });

    it('should create an instance of Symptom', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('ab6ebb18-4141-4139-99cf-380c395f0c78')
        .invoke('val')
        .should('match', new RegExp('ab6ebb18-4141-4139-99cf-380c395f0c78'));

      cy.get(`[data-cy="observation"]`).type('Zimbabwe Spurs').should('have.value', 'Zimbabwe Spurs');

      cy.setFieldImageAsBytesOfEntity('symptomImage', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="createdBy"]`).type('40551').should('have.value', '40551');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T12:24').should('have.value', '2022-08-02T12:24');

      cy.get(`[data-cy="updatedBy"]`).type('35877').should('have.value', '35877');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T17:59').should('have.value', '2022-08-02T17:59');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        symptom = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', symptomPageUrlPattern);
    });
  });
});
