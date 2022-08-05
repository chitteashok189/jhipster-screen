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

describe('Person e2e test', () => {
  const personPageUrl = '/person';
  const personPageUrlPattern = new RegExp('/person(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const personSample = {};

  let person: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/people+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/people').as('postEntityRequest');
    cy.intercept('DELETE', '/api/people/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (person) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/people/${person.id}`,
      }).then(() => {
        person = undefined;
      });
    }
  });

  it('People menu should load People page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('person');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Person').should('exist');
    cy.url().should('match', personPageUrlPattern);
  });

  describe('Person page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(personPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Person page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/person/new$'));
        cy.getEntityCreateUpdateHeading('Person');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/people',
          body: personSample,
        }).then(({ body }) => {
          person = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/people+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [person],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(personPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Person page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('person');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });

      it('edit button click should load edit Person page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Person');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });

      it('last delete button click should delete instance of Person', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('person').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);

        person = undefined;
      });
    });
  });

  describe('new Person page', () => {
    beforeEach(() => {
      cy.visit(`${personPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Person');
    });

    it('should create an instance of Person', () => {
      cy.get(`[data-cy="gUID"]`)
        .type('3543eda8-ee82-4a89-b8b3-1938d2cf2e81')
        .invoke('val')
        .should('match', new RegExp('3543eda8-ee82-4a89-b8b3-1938d2cf2e81'));

      cy.get(`[data-cy="salutation"]`).type('Strategist').should('have.value', 'Strategist');

      cy.get(`[data-cy="firstName"]`).type('Marcus').should('have.value', 'Marcus');

      cy.get(`[data-cy="middleName"]`).type('up').should('have.value', 'up');

      cy.get(`[data-cy="lastName"]`).type('Harris').should('have.value', 'Harris');

      cy.get(`[data-cy="personalTitle"]`).type('Surinam').should('have.value', 'Surinam');

      cy.get(`[data-cy="suffix"]`).type('e-business Practical').should('have.value', 'e-business Practical');

      cy.get(`[data-cy="nickName"]`).type('reciprocal incremental').should('have.value', 'reciprocal incremental');

      cy.get(`[data-cy="firstNameLocal"]`)
        .type('Cambridgeshire Wooden cross-platform')
        .should('have.value', 'Cambridgeshire Wooden cross-platform');

      cy.get(`[data-cy="middleNameLocal"]`).type('Fresh').should('have.value', 'Fresh');

      cy.get(`[data-cy="lastNameLocal"]`).type('Intranet Investor Toys').should('have.value', 'Intranet Investor Toys');

      cy.get(`[data-cy="otherLocal"]`)
        .type('efficient Buckinghamshire Marketing')
        .should('have.value', 'efficient Buckinghamshire Marketing');

      cy.get(`[data-cy="gender"]`).type('white digital').should('have.value', 'white digital');

      cy.get(`[data-cy="birthDate"]`).type('2022-08-02T15:47').should('have.value', '2022-08-02T15:47');

      cy.get(`[data-cy="heigth"]`).type('74124').should('have.value', '74124');

      cy.get(`[data-cy="weight"]`).type('1163').should('have.value', '1163');

      cy.get(`[data-cy="mothersMaidenName"]`).type('Rupee innovative Wooden').should('have.value', 'Rupee innovative Wooden');

      cy.get(`[data-cy="maritialStatus"]`).type('Toys Steel Automotive').should('have.value', 'Toys Steel Automotive');

      cy.get(`[data-cy="socialSecurityNo"]`).type('8258').should('have.value', '8258');

      cy.get(`[data-cy="passportNo"]`).type('quantifying Ameliorated').should('have.value', 'quantifying Ameliorated');

      cy.get(`[data-cy="passportExpiryDate"]`).type('Kentucky Cotton Peso').should('have.value', 'Kentucky Cotton Peso');

      cy.get(`[data-cy="totalYearsWorkExperience"]`).type('12321').should('have.value', '12321');

      cy.get(`[data-cy="comments"]`).type('Future').should('have.value', 'Future');

      cy.get(`[data-cy="occupation"]`).type('deposit').should('have.value', 'deposit');

      cy.get(`[data-cy="yearswithEmployer"]`).type('85889').should('have.value', '85889');

      cy.get(`[data-cy="monthsWithEmployer"]`).type('48131').should('have.value', '48131');

      cy.get(`[data-cy="existingCustomer"]`).type('96486').should('have.value', '96486');

      cy.get(`[data-cy="createdBy"]`).type('30725').should('have.value', '30725');

      cy.get(`[data-cy="createdOn"]`).type('2022-08-03T02:42').should('have.value', '2022-08-03T02:42');

      cy.get(`[data-cy="updatedBy"]`).type('42450').should('have.value', '42450');

      cy.get(`[data-cy="updatedOn"]`).type('2022-08-02T13:55').should('have.value', '2022-08-02T13:55');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        person = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', personPageUrlPattern);
    });
  });
});
