import { Given, Then } from "@badeball/cypress-cucumber-preprocessor";

const url = 'https://google.com'

Given('I open Google page', () => {
  // cy.visit(url)
  cy.visit('/')
});

Then('I see "Google" in the title', () => {
  cy.title().should('eq', 'Google');

});


