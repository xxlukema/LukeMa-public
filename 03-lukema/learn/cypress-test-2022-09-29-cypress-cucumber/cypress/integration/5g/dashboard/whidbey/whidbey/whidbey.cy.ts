import { Then, When } from 'cypress-cucumber-preprocessor/steps';

When('I click on {string} block', (field) => {
  cy.get('#air-field').click();
});

Then('It should take me to air field details page', () => {
  cy.contains('Whidbey xRAN Map').should('be.visible');
  cy.contains('site ID').should('be.visible');
  cy.wait(500);
});

When('I click on airfield map icon', () => {
  cy.get('#snap').click();
});

Then('It should take me back to whidbey homepage', () => {
  cy.contains('Hide Facility Blocks').should('be.visible');
});

after(() => {
  cy.logout();
});
