describe('generate reports', () => {

  let user: any;

  before(() => {
    cy.loginAsTest();
  });

  it('when I click on "Reports", I should see "Generate Reports', () => {
    cy.visit('/');
    cy.get('#toggleFacs').should('be.visible');
    cy.get('app-sidebar-nav-dropdown').contains('Reports').click();
    cy.get('app-sidebar-nav-link-content').contains('Generate Reports').should('be.visible');
  });

  it('when I click on "Generate Reports", it should take me to "Generate Reports" page', () => {
    cy.get('app-sidebar-nav-link-content').contains('Generate Reports').click();
    cy.contains('Create').should('be.visible');
  });

  it('I should be about to create "Non-Recurring Report"', () => {
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

  it('when I remove id data, then "Generate Report" button is disabled', () => {
    cy.get('input').get('[formControlName="id"').clear();
    cy.get('button').get('#submit').should('not.be.enabled');

    /**
     * Pause on purpose for demo
     */
    cy.wait(1_000);
  });

  it('when I click "Reset" button, then page data should be reset', () => {
    cy.get('button').contains('Reset').click();

    /**
     * TODO: Verify fields are empty
     */
    cy.get('button').get('#submit').should('not.be.enabled');
  });

  it('when I fill the form, I should be able to save parameters', () => {
    cy.get('[name="reportType"]').click();
    cy.get('mat-option').contains('Mission Usage Report').click();
    cy.get('input').get('[formControlName="id"').type('2345');
    cy.get('input').get('[formControlName="startDate"').click();
    cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
    cy.get('input').get('[formControlName="endDate"').click();
    cy.get('button').get('[class="mat-calendar-body-cell mat-calendar-body-active"').click();
    cy.get('button').get('#submit').should('be.enabled');
    cy.get('button').contains('Save Parameters').click();

    /**
     * Forced failure on purpose for demo only
     */
    // cy.contains('Report Parameters saved.').should('be.visible');
    cy.contains('Report Parameters saved.').should('not.be.visible');
  });

  it('when I click "Cancel" button, it should take me to "All Reports" page', () => {
    cy.get('button').contains('Cancel').click();
    cy.get('span[tabindex="0"]').contains('Reports').should('be.visible');
  });


  after(() => {
    cy.logout();
  });

});

