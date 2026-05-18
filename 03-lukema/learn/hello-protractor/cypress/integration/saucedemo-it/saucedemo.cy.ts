
describe('When I open https://www.saucedemo.com, I should see login prompt', () => {

  const url = 'https://www.saucedemo.com'

  it('When I open the page', () => {
    cy.visit(url);
  })

  it('Then I should see login prompts', () => {
    cy.title().should('eq', 'Swag Labs')

    console.log('================= titile', cy.get('title'))
  })

  it('When I login', () => {
    cy.get('#user-name').type('standard_user')
    cy.get('#password').type('secret_sauce')
    cy.get('#login-button').click()
  })

  it('Then I should be logged in', () => {
    cy.contains('Products').should('be.visible');
  })
})
