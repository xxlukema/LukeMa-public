/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
// declare global {
//   namespace Cypress {
//     interface Chainable {
//       login(email: string, password: string): Chainable<void>
//       drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
//     }
//   }
// }

// import 'cypress-keycloak';


declare namespace Cypress {
  interface Chainable<Subject = any> {
    login(username: string, password: string): Chainable<string>;
    loginAs(username: string): Chainable<any>;
    loginAsTest(): Chainable<any>;
    kcLogout(): Chainable<any>;
    loginAsWhidbey(): Chainable<any>;
    logout(): Chainable<any>;
  }
}

Cypress.Commands.add('logout', () => {
  cy.kcLogout();
  cy.visit('/login');
});

Cypress.Commands.add('kcLogout', () => {
  const kcRoot = Cypress.env('kcRoot');
  const kcRealm = Cypress.env('kcRealm');
  const kcRedirectUri = Cypress.config().baseUrl;
  return cy.request({
    url: `${kcRoot}/auth/realms/${kcRealm}/protocol/openid-connect/logout`,
    qs: {
      redirect_uri: kcRedirectUri
    }
  });
});

Cypress.Commands.add('login', (username: string, password: string) => {
  cy.kcLogout();
  cy.visit('/login');
  cy.get('#kc-login').should('be.visible');
  cy.get('#username').type(username);
  cy.get('#password').type(password);
  cy.get('#kc-login').click();
  cy.title().should('eq', 'Hughes');
  cy.url().should('include', '/home/');
});

Cypress.Commands.add('loginAs', (username: string) => {
  cy.login(username, 'test');
});

Cypress.Commands.add('loginAsTest', () => {
  cy.login('test', 'test');
});

Cypress.Commands.add('loginAsWhidbey', () => {
  cy.login('whidbey2', 'test');
});
