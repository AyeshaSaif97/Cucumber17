Feature: add employee scenario

  Background:
    When user enters admin username and password
    And user clicks on login button
    Then user is successfully logged in
    When user clicks on PIM option
    And user clicks on add employee option


  @addemp @test @excel
  Scenario: Adding one employee in the hrms system
    #Given user is navigated to HRMS application
   # When user enters admin username and password
   # And user clicks on login button
    #Then user is successfully logged in
    #When user clicks on PIM option
    #And user clicks on add employee option
    When user enters firstName middleName and lastName
    And user clicks on save button
    Then employee added successfully
   # Then user closes the browser



  @cucumber @excel
  Scenario: Adding one employee using feature file
    #When user enters admin username and password
    #And user clicks on login button
    #Then user is successfully logged in
    #When user clicks on PIM option
    #And user clicks on add employee option
    When user enters "miraj" and "fali" and "moralejo"
    And user clicks on save button
    Then employee added successfully

  @ddt @excel
  Scenario Outline: adding multiple employees from feature file
    When user enters "<firstName>" and "<middleName>" and enters "<lastName>"
    And user clicks on save button
    Then employee added successfully
    Examples:
      | firstName | middleName | lastName |
      | Elaine    | Musk       | Elon     |
      | Tahiti    | Agent      | Shield   |
      | Biden     | Peggy      | Carter   |

  @new
  Scenario: Adding multiple employees from excel file
    When user adds multiple employees from excel using "Sheet1" and verify them

  @datatable
  Scenario: adding multiple employees from data table
    When user adds multiple employees from data table
      | firstName | middleName | lastName |
      | Ela       | Musk       | Eon      |
      | Tahii     | Agent      | Sheld    |
      | Bidn      | Pegy       | Crter    |


    @db
    Scenario: Add Employee from Frontend and verify from DB
      When user enters "fahim" and "naughty" and "hedaiy"
      And user clicks on save button
      Then employee added successfully
      And fetch employee info from backend
      Then verify employee info is properly stored in db



