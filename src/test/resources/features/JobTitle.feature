Feature: add Job Title feature

  Background:
    When user enters admin username and password
    And user clicks on login button
    Then user is successfully logged in

  @jobTitle
  Scenario: add  new job Title
    When user clicks on admin button
    And user clicks on Job button
    And user clicks on Job Title button
    And user clicks on Add Button
    And user enters jobTitle "Fastest QA" desc "automation" note "Exceptional very well"
    And user clicks on the save button
    And verify same info from backend
