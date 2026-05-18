import { Then, When } from 'cypress-cucumber-preprocessor/steps';

Then('I should be able to create {string}', (sel) => {
  cy.get('mat-radio-button').contains('Non-Recurring Report').click();
  cy.get('[name="reportType"]').click();
  cy.get('mat-option').contains('Mission Usage Report').click();
  cy.get('input').get('[formControlName="id"').type('2345');
  cy.get('input').get('[formControlName="startDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
  cy.get('input').get('[formControlName="endDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
  cy.get('button').get('#submit').should('be.enabled');
});

When('I remove id data', () => {
  cy.get('input').get('[formControlName="id"').clear();
});

Then('page data should be reset', () => {
  /**
   * TODO: Verify fields are reset
   */
  cy.get('button').get('#submit').should('not.be.enabled');
});

When('I fill the form', () => {
  cy.get('[name="reportType"]').click();
  cy.get('mat-option').contains('Mission Usage Report').click();
  cy.get('input').get('[formControlName="id"').type('2345');
  cy.get('input').get('[formControlName="startDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
  cy.get('input').get('[formControlName="endDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
});

Then('I should be able to save parameters', () => {
  cy.get('button').get('#submit').should('be.enabled');
  cy.get('button').contains('Save Parameters').click();
  cy.contains('Report Parameters saved.').should('be.visible');
});

after(() => {
  cy.logout();
});
