@tag
Feature: Title of your feature
  I want to use this template for my feature file

  @tag1
  Scenario: Title of your scenario
    Given I open browser with url
    | url                                 |
#    | https://example.testproject.io/web/ |
    | http://localhost:4200/ |
    And I enter fullname and password
    | fullname    | password      |
    | Luke Ma     | 12345         |
    When I click login button
    Then I can verify my login

