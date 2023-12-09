Feature: Syntax API workflow feature


  Background:
    Given a JWT is generated

  @api
  Scenario: create an employee via API
    Given a request is prepared to create an employee
    When a POST call is made to create an employee
    Then the status code for this request is 201
    And the employee id "Employee.employee_id" is stored as global variable for other request



    @api
    Scenario: get the created employee
      Given a request is prepared to get the created employee
      When a GET call is made to get the employee
      Then the status code for ths request is 200
      And the global employee id must match with "employee.employee_id" key
      And the retrieved data at "employee" object matches the data used to create employee
      |emp_firstname|emp_lastname|emp_middle_name|emp_gender|emp_birthday|emp_status|emp_job_title|
      |Ayesha        |Pak        |ms             |Female    |1997-12-25  |confirmed |QA           |




      @api
      Scenario: Creating the employee using json payload
        Given a request is prepared for creating an employee via json payload
        When a POST call is made to create an employee
        Then the status code for this request is 201
        And the employee id "Employee.employee_id" is stored as global variable for other request
        And the response body contains "Message" key and value "Employee Created"

        @jsondynamic @update
        Scenario: Creating the employee using json payload
          Given a request is prepared for creating an employee with dynamic data "Ayesha","Pak","ms","F","1997-12-25","confirmed","qa"
          When a POST call is made to create an employee
          Then the status code for this request is 201
          And the employee id "Employee.employeeP_id" is stored as global variable for other request
          And the response body contains "Message" key and value "Employee Created"

     @update
Scenario: Updating the employee
  Given a request is prepared to update the employee an employee in HRMS system
  When a PUT call has been made
  Then the status code for updating the employee is 200
       And the response body contains "Message" key and value "Employee record Updated"


       @AllEmployees @test1
       Scenario: Getting all employees
         Given a request is prepared for getting all employees in the HRMS System
         When a GET call is made to get all the employees
         Then the status code for ths request is 200


         @AllJobTitles @test1
         Scenario: Getting all job titles
           Given a request is prepared for getting all job titles in the HRMS System
           When a GET call is made to get all the job titles
           Then the status code for ths request is 200
           And the response should contain a list of job titles

           @PartiallyUpdateEmployee @test1
           Scenario: Partially updating an employee
             Given a request call is prepared to partially update an employee in HRMS System
             When a Patch call has been made
             Then the status code for partially updating the employee is 201
             And the response body contains "Message" key and value "Employee record updated successfully"










