Feature: Whidbey Map

  Scenario: Landing Page
    Given I logged in as "test" user
    Then I should see in whidbey map

    When I click on "Air Field" block
    Then It should take me to air field details page

    When I click on airfield map icon
    Then It should take me back to whidbey homepage
