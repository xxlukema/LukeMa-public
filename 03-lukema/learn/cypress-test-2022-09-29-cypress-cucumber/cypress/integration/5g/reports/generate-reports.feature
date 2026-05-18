Feature: Generate Reports

  Scenario: Generate Reports
    Given I logged in as "test" user

    When I click on "Reports" nav button
    Then I should see "Generate Reports" nav link

    When I click on "Generate Reports" nav link
    Then It should take me to "Create" page
    Then I should be able to create "Non-Recurring Report"

    When I remove id data
    Then "Generate Report" button is disabled

    When I click on "Reset" button
    Then page data should be reset

    When I fill the form
    Then I should be able to save parameters

    When I click on "Cancel" button
    Then It should take me to "Reports" page
