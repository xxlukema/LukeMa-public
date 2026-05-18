describe('login to activedash', () => {

  let user: any;

  before(() => {
    cy.fixture('test').then(e => { user = e; });
  });

  it('when I open activedash home page, I should see login prompt', () => {
    cy.visit('/logout');
    cy.url().then(url => {
      cy.visit('/');
    });
    cy.get('#kc-login').should('be.visible');
  });

  it('when I login as test user, I should be at the home page of activedash', () => {
    cy.get('#username').type(user.username);
    cy.get('#password').type(user.password);
    cy.get('#kc-login').click();
    cy.title().should('eq', 'Hughes');
    cy.url().should('include', '/home/');
  });

  it('when I click "Logout" button, it should log me out', ()=>{
    cy.contains('Logout').click();
    cy.contains('Login').should('be.visible');
  });

});
