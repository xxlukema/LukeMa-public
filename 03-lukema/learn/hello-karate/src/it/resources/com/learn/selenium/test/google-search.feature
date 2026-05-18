@GoogleSearch
Feature: Feature to test google search  functionality

  @GoogleSearchScenario
  Scenario: Validate google search is working
    Given Browser is open
    And user is on google sarch page
    When user enters a text in search box
    And hits enter
    Then user is navigated to serach results