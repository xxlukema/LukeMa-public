Feature: XRAN gNodeB

  Scenario: List Page

    Given I logged in as "test" user

    When I click on "Dashboards" nav button
    Then I should see "gNodeB/xRAN" nav link

    When I click on "gNodeB/xRAN" nav link
    Then It should take me to "XRAN gNodeB" page

  Scenario: Details Page

    When I click on the first row
    Then After 2000 milliseconds
    Then It should take me to "Details" page
    And I should see "Selected QCI" label

    When I select "Conversational Video" from "qci" dropdown
    Then After 2000 milliseconds
    Then I should see "Active UEs" label

    When I click on "XRAN gNodeB" breadcrumb item
    Then It should take me to "XRAN gNodeB" page
