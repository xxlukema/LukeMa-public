@tag
Feature: Title: Is it Friday yet?
  Everybody wants to know when it's Friday

  # When you have tests that extends to having examples, you should always use Scenario Outline. 
  # Scenario is for those kind of tests with not more than one data to be inputted to test.
  @tag1
  Scenario Outline: Subtitle: Sunday isn't Friday
    Given today is "<day>"
    When I ask whether it's Friday yet
    Then I should be told "<answer>"

    # When you have tests that extends to having examples, you should always use Scenario Outline. 
    # Scenario is for those kind of tests with not more than one data to be inputted to test.
    @tag
    Examples:
      | day            | answer |
      | Friday         | TGIF   |
      | Sunday         | Nope   |
      | anything else! | Nope   |
    
    
    
    
    
    