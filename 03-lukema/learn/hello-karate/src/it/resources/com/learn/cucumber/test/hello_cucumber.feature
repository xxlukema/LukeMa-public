@HelloCucumer
Feature: Hello Cucumber Feature
  I want to use this template for my feature file

  @MyCucumberTag
  @HelloCucumerSimpleScenario
  Scenario: Hello Cucumer Simple Scenario
    Given I want to write a step with precondition
    And some other precondition
    When I complete action
    And some other action
    And yet another action
    Then I validate the outcomes
    And check more outcomes

  @HelloCucumerScenarioOutline
  Scenario Outline: Hello Cucumber Scenario Outline
    Given I want to write a step with "<name>"
    When I check for the <value> in step
    Then I verify the "<status>" in step

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
