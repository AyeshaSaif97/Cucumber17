package APISteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class APIWorkflowSteps {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    public static String token;
    RequestSpecification request;
    Response response;
    public static String employee_id;

    @Given("a JWT is generated")
    public void a_jwt_is_generated() {
        /*
        request = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "  \"email\": \"Aysh01@batch17.com\",\n" +
                        "  \"password\": \"Test@123\"\n" +
                        "}");
        Response response = request.when().post(APIConstants.GENERATE_TOKEN_URI);
        //storing the token after generating it
        token = "Bearer " + response.jsonPath().getString("token");
        System.out.println(token);
    }

         */
        request = given().
                header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                body(APIPayloadConstants.generateTokenPayload());
        Response response = request.when().post(APIConstants.GENERATE_TOKEN_URI);
        //storing the token after generating it
        token = "Bearer " + response.jsonPath().getString("token");
        System.out.println(token);

    }

    @Given("a request is prepared to create an employee")
    public void a_request_is_prepared_to_create_an_employee() {
        /*
        request = given().header(APIConstants.Header_Content_Type_Key,APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token)
                .body("{\n" +
                        "  \"emp_firstname\": \"Ayesha\",\n" +
                        "  \"emp_lastname\": \"Pak\",\n" +
                        "  \"emp_middle_name\": \"ms\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"1997-12-25\",\n" +
                        "  \"emp_status\": \"confirmed\",\n" +
                        "  \"emp_job_title\": \"qa\"\n" +
                        "}");

         */
        request = given().header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token)
                .body(APIPayloadConstants.createEmployeePayload());


    }

    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response = request.when().post(APIConstants.CREATE_EMPLOYEE_URI);
        //to print the response in console
        response.prettyPrint();
    }

    @Then("the status code for this request is {int}")
    public void the_status_code_for_this_request_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
        //validate the body
        response.then().assertThat().
                body("Message", equalTo("Employee Created"));
        response.then().assertThat().
                body("Employee.emp_firstname", equalTo("Ayesha"));
        response.then().assertThat().
                header("Connection", equalTo("Keep-Alive"));
    }

    @Then("the employee id {string} is stored as global variable for other request")
    public void the_employee_id_is_stored_as_global_variable_for_other_request(String empId) {
//empId is the parameter coming from feature file which is the path of employee
        employee_id = response.jsonPath().getString(empId);
        System.out.println(employee_id);


    }

    //_______________________________________________________________________________________________//

    @Given("a request is prepared to get the created employee")
    public void a_request_is_prepared_to_get_the_created_employee() {
        request = given().
                header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token).
                queryParam("employee_id", employee_id);

    }

    @When("a GET call is made to get the employee")
    public void a_get_call_is_made_to_get_the_employee() {
        response = request.when().get(APIConstants.GET_ONE_EMPLOYEE_URI);
        response.prettyPrint();
    }

    @Then("the status code for ths request is {int}")
    public void theStatusCodeForThsRequestIs(int statusCode) {
        response.then().assertThat().statusCode(statusCode);

    }


    @Then("the global employee id must match with {string} key")
    public void the_global_employee_id_must_match_with_key(String empId) {
        String tempEmpId = response.jsonPath().
                getString(empId);
        Assert.assertEquals(tempEmpId, employee_id);

    }

    @Then("the retrieved data at {string} object matches the data used to create employee")
    public void the_retrieved_data_at_object_matches_the_data_used_to_create_employee(String employeeObject, io.cucumber.datatable.DataTable dataTable) {
        //one map comes from data table
        List<Map<String, String>> expectedData = dataTable.asMaps();
        //another map comes from employee object response
        //it is going to return the whole map, getString returns just one value
        Map<String, String> actualData = response.body().jsonPath().get(employeeObject);

        for (Map<String, String> map : expectedData) {
            //storing all the keys under set
            Set<String> keys = map.keySet();
            //from set of keys to one key at one time
            for (String key : keys) {
                //this will return the value against key
                String expectedValue = map.get(key);

                //this will return the value against the employee object
                String actualValue = actualData.get(key);

                Assert.assertEquals(actualValue, expectedValue);


            }

        }


    }

    //_______________________________________________________________________________________________________________//


    @Given("a request is prepared for creating an employee via json payload")
    public void a_request_is_prepared_for_creating_an_employee_via_json_payload() {
        request = given().
                header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token).body(APIPayloadConstants.createEmployeeJsonPayload());


    }

    @Then("the response body contains {string} key and value {string}")
    public void the_response_body_contains_key_and_value(String key, String value) {
        response.then().assertThat().body(key, equalTo(value));

    }
//________________________________________________________________________________________________________________//

    @Given("a request is prepared for creating an employee with dynamic data {string},{string},{string},{string},{string},{string},{string}")
    public void a_request_is_prepared_for_creating_an_employee_with_dynamic_data(String firstname, String lastname, String middlename, String gender, String birthday, String status, String jobtitle) {
        request = given().
                header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token).body(APIPayloadConstants.payloadDynamic(firstname, lastname, middlename, gender, birthday, status, jobtitle));
    }


    //_______________________________________________________________________________________________________________________//


    @Given("a request is prepared to update the employee an employee in HRMS system")
    public void a_request_is_prepared_to_update_the_employee_an_employee_in_hrms_system() {
        request = given().header(APIConstants.Header_Authorization_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token).body(APIPayloadConstants.updateEmployeePayload());

    }

    @When("a PUT call has been made")
    public void a_put_call_has_been_made() {
        response = request.when().put(APIConstants.UPDATE_EMPLOYEE_URI);
        response.prettyPrint();


    }

    @Then("the status code for updating the employee is {int}")
    public void the_status_code_for_updating_the_employee_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);

    }
//_______________________________________________________________________________________________________//

    @Given("a request is prepared for getting all employees in the HRMS System")
    public void a_request_is_prepared_for_getting_all_employees_in_the_hrms_system() {
        request = given().
                header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token);

    }

    @When("a GET call is made to get all the employees")
    public void a_get_call_is_made_to_get_all_the_employees() {
        response = request.when().get(APIConstants.GET_ALL_EMPLYOEE_URI);
        //to print the response in console
        response.prettyPrint();
    }

//_______________________________________________________________________________________________________//

    @Given("a request is prepared for getting all job titles in the HRMS System")
    public void a_request_is_prepared_for_getting_all_job_titles_in_the_hrms_system() {
        request = given().
                header(APIConstants.Header_Content_Type_Key, APIConstants.Content_Type_Value).
                header(APIConstants.Header_Authorization_Key, token);

    }

    @When("a GET call is made to get all the job titles")
    public void a_get_call_is_made_to_get_all_the_job_titles() {
        response = request.when().get(APIConstants.GET_JOB_TITLES);
        //to print the response in console
        response.prettyPrint();

    }

    @Then("the response should contain a list of job titles")
    public void the_response_should_contain_a_list_of_job_titles() {
        assertTrue(response.asString().contains("Jobs"));
    }
//____________________________________________________________________________________________________________//
@Given("a request call is prepared to partially update an employee in HRMS System")
public void a_request_call_is_prepared_to_partially_update_an_employee_in_hrms_system() {
    request = given().header(APIConstants.Header_Authorization_Key, APIConstants.Content_Type_Value).
            header(APIConstants.Header_Authorization_Key, token).body(APIPayloadConstants.partiallyUpdateEmployeePayload());
}
    @When("a Patch call has been made")
    public void a_patch_call_has_been_made() {
        response = request.when().patch(APIConstants.PARTIALLY_UPDATE_EMPLOYEE_URI);
        response.prettyPrint();

    }
    @Then("the status code for partially updating the employee is {int}")
    public void the_status_code_for_partially_updating_the_employee_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);

    }




}