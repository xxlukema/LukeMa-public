@IsItFridayYet
Feature: Title: Is it Friday yet?
  Everybody wants to know when it is Friday

  # When you have tests that extends to having examples, you should always use Scenario Outline.
  # Scenario is for those kind of tests with not more than one data to be inputted to test.
  @FridayOutline @MyCucumberTag
  Scenario Outline: Subtitle: Sunday is not Friday
    Given today is "<day>"
    When I ask whether it is Friday yet
    Then I should be told "<answer>"

    # When you have tests that extends to having examples, you should always use Scenario Outline.
    # Scenario is for those kind of tests with not more than one data to be inputted to test.
    @Weekdays
    Examples: 
      | day            | answer |
      | Friday         | TGIF   |
      | Sunday         | Nope   |
      | anything else! | Nope   |
