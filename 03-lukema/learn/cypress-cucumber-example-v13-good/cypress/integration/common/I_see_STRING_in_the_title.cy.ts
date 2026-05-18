import { Then } from "cypress-cucumber-preprocessor/steps";

Then('I see {string} in the title', title => {

  cy.log('------------------ seen:', title);

  cy.title().should("include", title);
});
