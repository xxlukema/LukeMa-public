import { Given, Then, When } from 'cypress-cucumber-preprocessor/steps';

Given('I logged in as {string} user', (username) => {
  cy.loginAs(username);
});

Then('I should see in whidbey map', () => {
  cy.visit('/');
  cy.contains('Hide Facility Blocks').should('be.visible');
});
