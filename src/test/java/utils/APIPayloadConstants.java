package utils;

import APISteps.APIWorkflowSteps;
import org.json.JSONObject;

public class APIPayloadConstants {
    //body another name of Payload


    public static String generateTokenPayload() {
        String generateToken = "{\n" +
                "  \"email\": \"Aysh01@batch17.com\",\n" +
                "  \"password\": \"Test@123\"\n" +
                "}";
        return generateToken;

    }

    public static String createEmployeePayload() {
        String createEmployeePayload = "{\n" +
                "  \"emp_firstname\": \"Ayesha\",\n" +
                "  \"emp_lastname\": \"Pak\",\n" +
                "  \"emp_middle_name\": \"ms\",\n" +
                "  \"emp_gender\": \"F\",\n" +
                "  \"emp_birthday\": \"1997-12-25\",\n" +
                "  \"emp_status\": \"confirmed\",\n" +
                "  \"emp_job_title\": \"qa\"\n" +
                "}";
        return createEmployeePayload;


    }

    public static String createEmployeeJsonPayload() {
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", "Ayesha");
        obj.put("emp_lastname", "Pak");
        obj.put("emp_middle_name", "ms");
        obj.put("emp_gender", "F");
        obj.put("emp_birthday", "1997-12-25");
        obj.put("emp_status", "confirmed");
        obj.put("emp_job_title", "qa");

        return obj.toString();

    }

    public static String payloadDynamic(String emp_firstname, String emp_lastname, String emp_middle_name, String emp_gender, String emp_birthday,
                                        String emp_status, String emp_job_title) {

        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", emp_firstname);
        obj.put("emp_lastname", emp_lastname);
        obj.put("emp_middle_name", emp_middle_name);
        obj.put("emp_gender", emp_gender);
        obj.put("emp_birthday", emp_birthday);
        obj.put("emp_status", emp_status);
        obj.put("emp_job_title", emp_job_title);

        return obj.toString();

    }

    public static String updateEmployeePayload() {
        JSONObject obj = new JSONObject();
        //obj.put("employee_id", APIWorkflowSteps.employee_id);
        obj.put("employee_id","98338A");
        obj.put("emp_firstname", "Aysh");
        obj.put("emp_lastname", "Alghas");
        obj.put("emp_middle_name", "AA");
        obj.put("emp_gender", "F");
        obj.put("emp_birthday", "1997-12-02");
        obj.put("emp_status", "Confirmed");
        obj.put("emp_job_title", "Automation Tester");
        return obj.toString();

    }

    public static String partiallyUpdateEmployeePayload(){
        JSONObject obj=new JSONObject();
        obj.put("employee_id","96311A");
        obj.put("emp_middle_name","AALA");
        return obj.toString();
    }


}
