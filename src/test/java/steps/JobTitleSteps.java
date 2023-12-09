package steps;

import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.CommonMethods;
import utils.DbUtils;


public class JobTitleSteps extends CommonMethods {

    String jobTitleFFE;
    String jobDescFFE;
    String jobNoteFFE;


    @When("user clicks on admin button")
    public void user_clicks_on_admin_button() {
        click(adminDashBoardPage.adminButton1Locator);
    }

    @When("user clicks on Job button")
    public void user_clicks_on_job_button() {
        click(adminDashBoardPage.adminJobBtn);
    }

    @When("user clicks on Job Title button")
    public void user_clicks_on_job_title_button() {
        click(jobTitlePage.jobTitleButton);
    }

    @When("user clicks on Add Button")
    public void user_clicks_on_add_button() {
        click(jobTitlePage.addJobBtn);

    }

    @When("user enters jobTitle {string} desc {string} note {string}")
    public void user_enters_job_title_desc_note(String jobTitle, String jobDesc, String jobNote) {
        jobTitleFFE = jobTitle;
        jobDescFFE = jobDesc;
        jobNoteFFE = jobNote;
        sendText(jobTitlePage.addJobTitleField, jobTitle);
        sendText(jobTitlePage.addJobDesc, jobDesc);
        sendText(jobTitlePage.addJobNote, jobNote);


    }

    @When("user clicks on the save button")
    public void user_clicks_on_the_save_button() {
        click(jobTitlePage.addJobSvBtn);
    }

    @When("verify same info from backend")
    public void verify_same_info_from_backend() {
        String query = " select * from ohrm_job_title where job_title='" + jobTitleFFE + "';";
        var dataFromDb = DbUtils.fetch(query);
        String jobTitleFBE = dataFromDb.get(0).get("job_title");
        String jobDescFBE = dataFromDb.get(0).get("job_description");
        String jobNoteFBE = dataFromDb.get(0).get("note");
        Assert.assertEquals("Field didnt match", jobTitleFFE, jobTitleFBE);
        Assert.assertEquals("Field didnt match", jobDescFFE, jobDescFBE);
        Assert.assertEquals("Field didnt match", jobNoteFFE, jobNoteFBE);


    }


}