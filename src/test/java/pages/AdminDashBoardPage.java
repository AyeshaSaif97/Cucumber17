package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class AdminDashBoardPage extends CommonMethods {
    @FindBy(xpath = "//b[text()='Dashboard']")
    public WebElement DashboardButtonLocator;
    @FindBy(xpath = "//a[text()='Welcome Admin']")
    public  WebElement welcomeAdminLocator;
    @FindBy(id="menu_admin_viewAdminModule")
    public  WebElement adminButton1Locator;

    @FindBy(id="menu_admin_Qualifications")
    public  WebElement qualificationsButtonLocator;

    @FindBy(id="menu_admin_viewLanguages")
    public  WebElement languagesButtonLocator;

    @FindBy(id="menu_pim_viewPimModule")
    public WebElement pimButtonLocator;
    @FindBy(id="menu_pim_addEmployee")
    public WebElement addEmployeeButtonLocator;

    @FindBy(xpath = "//a[text()='Logout']")
    public WebElement logoutAdminLocator;


    @FindBy(xpath = "//a[@id='menu_admin_Job']")
    public  WebElement adminJobBtn;

    @FindBy(xpath = "//a[@id='menu_admin_viewJobTitleList']")
    public  WebElement adminJobTitleBtn;


    public AdminDashBoardPage(){
        PageFactory.initElements(driver, this);
    }

}