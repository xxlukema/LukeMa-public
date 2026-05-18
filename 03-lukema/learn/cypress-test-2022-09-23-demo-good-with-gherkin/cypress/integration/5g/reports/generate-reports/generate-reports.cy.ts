/*
describe('generate reports', () => {

  let user: any;

  before(() => {
    cy.loginAsTest();
  });

  it('when I click on "Reports", I should see "Generate Reports', () => {
    cy.visit('/');
    cy.contains('Hide Facility Blocks').should('be.visible');
    cy.get('app-sidebar-nav-dropdown').contains('Reports').click();
  });

  it('when I click on "Generate Reports", it should take me to "Generate Reports" page', () => {
    cy.get('app-sidebar-nav-link-content').contains('Generate Reports').click();
    // cy.get('breadcrumb-item').contains('Create Report').should('be.visible');
    cy.contains('Create Report').should('be.visible');
  });

  it('I should be about to create "Non-Recurring Report"', () => {
    cy.get('mat-radio-button').contains('Non-Recurring Report').click();
    cy.get('#reportType').click();
    cy.get('mat-option').contains('Mission Usage Report').click();
    cy.get('input').get('[formControlName="id"').type('2345');
    cy.get('input').get('[formControlName="startDate"').click();
    cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
    cy.get('input').get('[formControlName="endDate"').click();
    cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
    // cy.get('button').contains('Generate Report').should('be.enabled');
  });

  it('when I remove id data, then "Generate Report" button is disabled', ()=>{
    cy.get('input').get('[formControlName="id"').clear();
    cy.get('button').contains('Generate Report').should('not.be.enabled');
    cy.wait(1_000);
  });

  it('when I click "Reset" button, then page data should be reset', ()=>{
    cy.get('button').contains('Reset').click();
    cy.get('button').contains('Generate Report').should('not.be.enabled');
  });

  it('when I fill the form, I should be able to save parameters', ()=>{
    cy.get('#reportType').click();
    cy.get('mat-option').contains('Mission Usage Report').click();
    cy.get('input').get('[formControlName="id"').type('2345');
    cy.get('input').get('[formControlName="startDate"').click();
    cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
    cy.get('input').get('[formControlName="endDate"').click();
    cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
    // cy.get('button').contains('Generate Report').should('be.enabled');
    cy.get('button').contains('Save Parameters').click();
    cy.contains('Report Parameters saved.').should('be.visible');
  });

  it('when I click "Cancel" button, it should take me to "All Reports" page', ()=>{
    cy.get('button').contains('Cancel').click();
    // cy.get('breadcrumb-item').contains('Error Details').should('be.visible');
    cy.contains('Error Details').should('be.visible');
  });


  after(() => {
    cy.logout();
  });

});
*/

import { Then, When } from 'cypress-cucumber-preprocessor/steps';


When('I click on {word} nav button', (btn)=>{
  cy.get('app-sidebar-nav-dropdown').contains(btn).click();
});

Then('I should see {string}', (btn)=>{
  cy.get('app-sidebar-nav-link-content').contains(btn).should('be.visible');
  // cy.get('app-sidebar-nav-link-content').contains(btn).should('be.enabled');
  cy.get('app-sidebar-nav-link-content').contains(btn).click();
});

Then('It should take me to {string} page', (page)=>{
  cy.contains(page).should('be.visible');
});

Then('I should be able to create {string}', (sel)=>{
  cy.get('mat-radio-button').contains(sel).click();
  cy.get('#reportType').click();
  cy.get('mat-option').contains('Mission Usage Report').click();
  cy.get('input').get('[formControlName="id"').type('2345');
  cy.get('input').get('[formControlName="startDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
  cy.get('input').get('[formControlName="endDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
});

When('I remove id data', ()=>{
  cy.get('input').get('[formControlName="id"').clear();
});

Then('{string} button is disabled', (btn)=>{
  cy.get('button').contains(btn).should('not.be.enabled');
  cy.wait(1_000);
});

When('I click on {word} button', (btn)=>{
  cy.get('button').contains(btn).click();
});

Then('page data should be reset', ()=>{
  cy.get('button').contains('Generate Report').should('not.be.enabled');
});

When('I fill the form', ()=>{
  cy.get('#reportType').click();
  cy.get('mat-option').contains('Mission Usage Report').click();
  cy.get('input').get('[formControlName="id"').type('2345');
  cy.get('input').get('[formControlName="startDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
  cy.get('input').get('[formControlName="endDate"').click();
  cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
  // cy.get('button').contains('Generate Report').should('be.enabled');
});

Then('I should be able to save parameters', ()=>{
  cy.get('button').contains('Save Parameters').click();
  cy.contains('Report Parameters saved.').should('be.visible');
});

Then('It should take me to {string} page', (pg)=>{
  cy.contains(pg).should('be.visible');
});

after(() => {
  cy.logout();
});
