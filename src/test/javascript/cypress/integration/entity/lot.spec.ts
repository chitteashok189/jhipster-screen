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

describe('Lot e2e test', () => {
  const lotPageUrl = '/lot';
  const lotPageUrlPattern = new RegExp('/lot(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const lotSample = {};

  let lot: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/lots+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/lots').as('postEntityRequest');
    cy.intercept('DELETE', '/api/lots/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (lot) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/lots/${lot.id}`,
      }).then(() => {
        lot = undefined;
      });
    }
  });

  it('Lots menu should load Lots page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('lot');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Lot').should('exist');
    cy.url().should('match', lotPageUrlPattern);
  });

  describe('Lot page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(lotPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Lot page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/lot/new$'));
        cy.getEntityCreateUpdateHeading('Lot');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lotPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/lots',
          body: lotSample,
        }).then(({ body }) => {
          lot = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/lots+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [lot],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(lotPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Lot page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('lot');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lotPageUrlPattern);
      });

      it('edit button click should load edit Lot page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Lot');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lotPageUrlPattern);
      });

      it('last delete button click should delete instance of Lot', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('lot').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', lotPageUrlPattern);

        lot = undefined;
      });
    });
  });

  describe('new Lot page', () => {
    beforeEach(() => {
      cy.visit(`${lotPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Lot');
    });

    it('should create an instance of Lot', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('4cf1921a-9c81-42a6-a63c-d3bce38cf48a')
        .invoke('val')
        .should('match', new RegExp('4cf1921a-9c81-42a6-a63c-d3bce38cf48a'));

      cy.get(`[data-cy="lotCode"]`).type('Producer Mauritius').should('have.value', 'Producer Mauritius');

      cy.setFieldImageAsBytesOfEntity('lotQRCode', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="lotSize"]`).type('61392').should('have.value', '61392');

      cy.get(`[data-cy="unitType"]`).select('Cells');

      cy.get(`[data-cy="seedlingsGerminated"]`).type('95644').should('have.value', '95644');

      cy.get(`[data-cy="seedlingsDied"]`).type('15354').should('have.value', '15354');

      cy.get(`[data-cy="plantsWasted"]`).type('9662').should('have.value', '9662');

      cy.get(`[data-cy="traysSown"]`).type('78500').should('have.value', '78500');

      cy.get(`[data-cy="sowingTime"]`).select('Weekly');

      cy.get(`[data-cy="traysTranplanted"]`).type('98595').should('have.value', '98595');

      cy.get(`[data-cy="transplantationTime"]`).select('Weekly');

      cy.get(`[data-cy="plantsHarvested"]`).type('83354').should('have.value', '83354');

      cy.get(`[data-cy="harvestTime"]`).select('Monthly');

      cy.get(`[data-cy="packingDate"]`).type('2022-08-02T12:34').should('have.value', '2022-08-02T12:34');

      cy.get(`[data-cy="tranportationDate"]`).type('2022-08-02T14:20').should('have.value', '2022-08-02T14:20');

      cy.get(`[data-cy="createdBy"]`).type('83191').should('have.value', '83191');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-02T10:48').should('have.value', '2022-08-02T10:48');

      cy.get(`[data-cy="updatedBy"]`).type('90172').should('have.value', '90172');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-03T03:51').should('have.value', '2022-08-03T03:51');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        lot = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', lotPageUrlPattern);
    });
  });
});
