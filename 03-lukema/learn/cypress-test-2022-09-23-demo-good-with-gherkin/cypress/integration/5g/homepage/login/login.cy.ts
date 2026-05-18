import { Given, Then, Before, When } from "cypress-cucumber-preprocessor/steps";

/*
beforeEach(() => {
  cy.exec('npm cache clear --force');
});
*/

Given('I open activedash home page', () => {
  cy.visit('/');
});

Then('I should see login prompt', () => {
  cy.url().then(url => {
    cy.visit('/');
  });
  cy.get('#kc-login').should('be.visible');
});

When('I login as {string} user', (username) => {
  cy.loginAs(username);
});

Then('I should be at the home page of activedash', () => {
  cy.title().should('eq', 'Hughes');
  cy.url().should('include', '/home/');
});

When(`I click {string} button`, (logout) => {
  cy.contains(logout).click();
});

Then('It should log me out', () => {
  cy.contains('Login').should('be.visible');
});
