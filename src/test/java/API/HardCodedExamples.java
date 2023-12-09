package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HardCodedExamples {

    //in rest assured base uri = base URL
  //  String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDE1MzIwNjksImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcwMTU3NTI2OSwidXNlcklkIjoiNjE5NSJ9.iRqEGEd4clrk_cDsVMBaHDYQPOjd03IMYb5hE_NHTBE";
    static String employee_id;


    @Test

    public void acreateEmployee() {
        //prepare the request
        //request specification allows us to prepare the request and gives it in variables
        RequestSpecification request = given().header("Content-type", "application/json").header("Authorization", token)
                .body("\n" +
                        "{\n" +
                        "  \"emp_firstname\": \"Iron\",\n" +
                        "  \"emp_lastname\": \"Man\",\n" +
                        "  \"emp_middle_name\": \"TS\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1980-11-26\",\n" +
                        "  \"emp_status\": \"Employed\",\n" +
                        "  \"emp_job_title\": \"Robot\"\n" +
                        "}");

        //response = holds the complete response of the request
        //send the request for creating the employee
        //we validate different entities in response
        Response response = request.when().post("/createEmployee.php");

        //to print the response in console
        response.prettyPrint();


        //validate the response status
        response.then().assertThat().statusCode(201);

//validate the body
        response.then().assertThat().body("Message", equalTo("Employee Created"));
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Iron"));
        response.then().assertThat().header("Connection", equalTo("Keep-Alive"));

        //to store the employee id after generating the employee
        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);

    }


    @Test

    public void bgetCreatedEmployee() {
        //prepare the request
        RequestSpecification request = given().header("Content-type", "application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);

        Response response = request.when().get("/getOneEmployee.php");

        response.prettyPrint();

        response.then().assertThat().statusCode(200);

        //validate the employee id's one from post call another from get call
        String tempEmpId = response.jsonPath().getString("employee.employee_id");
        Assert.assertEquals(tempEmpId, employee_id);


    }

    @Test
    public void cUpdateEmployee() {
        RequestSpecification request = given().header("Content-Type", "application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"employee_id\": \"" + employee_id + "\",\n" +
                        "  \"emp_firstname\": \"ayesha\",\n" +
                        "  \"emp_lastname\": \"alghas\",\n" +
                        "  \"emp_middle_name\": \"AA\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"1992-04-10\",\n" +
                        "  \"emp_status\": \"DONT KNOW\",\n" +
                        "  \"emp_job_title\": \"FAST QA\"\n" +
                        "}");

        Response response = request.when().put("/updateEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);


    }

    @Test

    public void dgetUpdatedEmployee() {
        //prepare the request
        RequestSpecification request = given().header("Content-type", "application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);

        Response response = request.when().get("/getOneEmployee.php");

        response.prettyPrint();

        response.then().assertThat().statusCode(200);

        //validate the employee id's one from post call another from get call
        String tempEmpId = response.jsonPath().getString("employee.employee_id");
        Assert.assertEquals(tempEmpId, employee_id);


    }


}
