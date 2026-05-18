Feature: saucedemo logon

  I want to logon to saucedemo

  @focus
  Scenario: Opening saucedemo page
    Given I open saucedemo page
      | url                       |
      | https://www.saucedemo.com |
    Then I should see "Swab Labs" title on the page tab
    And I enter <username> and <password>
      | username      | password     |
      | standard_user | secret_sauce |
    When I login
    Then I should see "Products"


