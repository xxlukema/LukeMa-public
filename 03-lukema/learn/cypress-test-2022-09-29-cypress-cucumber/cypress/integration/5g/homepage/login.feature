Feature: User Login

  Scenario: Login Prompt
    Given I open activedash home page
    Then I should see login prompt

    When I login as "test" user
    Then I should be at the home page of activedash

    When I click "Logout" button
    Then It should log me out
