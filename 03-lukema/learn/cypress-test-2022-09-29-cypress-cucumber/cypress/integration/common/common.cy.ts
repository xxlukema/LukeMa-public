import { And, Given, Then, When } from 'cypress-cucumber-preprocessor/steps';

Given('I logged in as {string} user', (username) => {
  cy.loginAs(username);
  cy.visit('/');
  cy.get('#toggleFacs').should('be.visible');
});

When('I click on {string} nav button', (btn) => {
  cy.get('app-sidebar-nav-dropdown').contains(btn).should('be.visible');
  cy.get('app-sidebar-nav-dropdown').contains(btn).click({ force: true });
});

When('I click on {string} button', (btn) => {
  cy.get('button').contains(btn).click();
});

Then('I should see {string} nav link', (btn) => {
  cy.get('app-sidebar-nav-items.nav-dropdown-items').get('app-sidebar-nav-link-content').contains(btn).should('be.visible');
});

When('I click on {string} nav link', (link) => {
  cy.get('app-sidebar-nav-dropdown').contains(link).click({ force: true });
  cy.wait(1_000);
});

Then('It should take me to {string} page', (page) => {
  // cy.contains(page).should('be.visible');
  // cy.get('span[tabindex="0"]').contains(page).should('be.visible');
  cy.get('cui-breadcrumb').get('li.breadcrumb-item.active').contains(page).should('be.visible');
});


And('I should see {string} label', (lbl) => {
  cy.get('mat-card-title').contains(lbl).should('be.visible');
});

Then('After {int} milliseconds', (seconds) => {
  cy.wait(seconds);
});

When('I click on {string} breadcrumb item', (item) => {
  cy.get('li.breadcrumb-item').contains(item).click();
});

When('I select {string} from {string} dropdown', (option, name) => {
  const attr = '[name="' + name + '"]';
  cy.get(attr).click();
  cy.get('mat-option').contains(option).click();
});

Then('{string} button is disabled', (btn) => {
  cy.get('button').get('#submit').should('not.be.enabled');
});
