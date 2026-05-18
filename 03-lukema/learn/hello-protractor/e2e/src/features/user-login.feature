#    | https://example.testproject.io/web/ |
@UserLoginFeature
Feature: Title of UserLogin feature
  I want to use this template for my feature file

  @UserLoginScenario
  Scenario: Title of UserLogin scenario
    Given I open browser with url
    | url                                 |
    | http://localhost:4200/ |
    And I enter fullname and password
    | fullname    | password      |
    | Luke Ma     | 12345         |
    When I click login button
    Then I can verify my login

