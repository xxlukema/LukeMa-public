describe('whidbey home page', () => {

  let user: any;

  before(() => {
    cy.loginAsTest();
  });

  it('when I open activedash, I should be in whidbey map page', () => {
    cy.visit('/');
    cy.contains('Hide Facility Blocks').should('be.visible');
  });

  it('when I click on "Air Field" block, it should take me to air field details page', () => {
    cy.get('#air-field').click();
    cy.contains('Details').should('be.visible');
    cy.wait(500);
  });

  it('when I click on air map icon, it should take me to whidbey homepage', () => {
    cy.get('#snap').click();
    cy.contains('Hide Facility Blocks').should('be.visible');
  });

  after(() => {
    cy.logout();
  });

});

