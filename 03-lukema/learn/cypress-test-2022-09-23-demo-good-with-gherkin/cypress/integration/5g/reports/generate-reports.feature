Feature: Generate Reports

  Scenario: Generate Reports
    Given I logged in as "test" user
    Then I should see in whidbey map

    When I click on "Reports" nav button
    Then I should see "Generate Reports"

    When I click on "Generate Reports"
    Then It should take me to "Create" page
    Then I should be able to create "Non-Recurring Report"

    # When I remove id data
    # Then "Generate Report" button is disabled

    # When I click on "Reset" button
    # Then page data should be reset

    # When I fill the form
    # Then I should be able to save parameters

    # When I click "Cancel" button
    # Then It should take me to "All Reports" page
