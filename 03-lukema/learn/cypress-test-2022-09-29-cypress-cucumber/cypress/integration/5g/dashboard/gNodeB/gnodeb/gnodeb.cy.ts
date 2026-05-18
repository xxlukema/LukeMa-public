import { When } from "cypress-cucumber-preprocessor/steps";

When('I click on the first row', () => {
  cy.get('table').get('tbody>tr:nth-child(1)').click();
});

after(() => {
  cy.logout();
});
