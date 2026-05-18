import { Given, Then, When } from "@badeball/cypress-cucumber-preprocessor";


const url = 'https://www.saucedemo.com'

Given('I open saucedemo page', (dataTable) => {
  cy.visit(url);
})

Then('I should see {string} title on the page tab', (str) => {
  cy.title().should('eq', 'Swag Labs')
})

Then('I enter <username> and <password>', (dataTable) => {
  cy.get('#user-name').type('standard_user')
  cy.get('#password').type('secret_sauce')
})

When('I login', () => {
  cy.get('#login-button').click()
})

Then('I should see {string}', (str) => {
  cy.contains('Products').should('be.visible');
})

