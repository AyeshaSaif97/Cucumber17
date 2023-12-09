package utils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class CommonMethods extends PageInitializer {

    public static WebDriver driver;  //interface


/**
 * This method navigates to the specified browser and opens any url
 */
    public static void openBrowserAndLaunchApplication() {
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);

        switch (ConfigReader.getPropertyValue("browser")) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "safari":
                driver = new SafariDriver();
            default:
                throw new RuntimeException("invalid browser name");

        }
        driver.manage().window().maximize();
        driver.get(ConfigReader.getPropertyValue("url"));
        initializePageObjects();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));

    }

    /**
     * this method send text to webElement with text box
     * @param element - webElement
     * @param text    - text which we need to send
     */
    public static void sendText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);

    }



    /**
     * this is method gets time of explicit wait
     * @return WebDriverWait
     */
    public static WebDriverWait getWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        return wait;
    }

    /**
     * this method wait until element will be clickable
     * @param element - webElement which we need to click
     */
    public static void WaitForClickAbility(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * this method makes click on webElement
     * @param element - webElement which we need to click
     */
    public static void click(WebElement element) {
        WaitForClickAbility(element);
        element.click();
    }

    public static JavascriptExecutor getJSExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;

    }

    /**
     * this method makes click on webElement
     * @param element webElement which we need to click using JS click
     */
    public static void jsClick(WebElement element) {
        getJSExecutor().executeScript("arguments[0].click();", element);
    }


    /**
     * this method closes browser
     */
    public void closeBrowser() {
        driver.quit();
    }


    //take screenshot method for capturing all the screenshots.
    /**
     * this method takes screenshot and save file in Screenshot folder
     * @param fileName - name for screenshot file
     * @return - screenshot in array of bytes for report
     */
    public static byte[] takeScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        //in cucumber screenshot should be taken in array of byte format
        //since the size of the image is in byte, thats why the return type of this method is array of byte
        byte[] picByte = ts.getScreenshotAs(OutputType.BYTES);
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        //we will pass the path of ss from constants class

        //month = Captial MM , minute = small m
        try {
            FileUtils.copyFile(sourceFile, new File(Constants.SCREENSHOT_FILEPATH + fileName+" "+getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picByte;
    }


    /**
     * this method returns date in String with specific pattern
     * @param pattern - YYYY-MM-DD-HH-MM-SS-MS
     * @return - data in String
     */
    public static String getTimeStamp(String pattern){
        Date date= new Date();
        //after getting the date, I need to format it as per my requirement
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        //it will return the formatted date as per the pattern in string format
        return sdf.format(date);
    }

    /**
     * Method checks if radio/checkbox is enabled and clicks it
     * @param radioOrcheckbox
     * @param value
     */
    public static void clickRadioOrCheckbox(List<WebElement> radioOrcheckbox, String value) {
        String actualValue;
        for (WebElement el : radioOrcheckbox) {
            actualValue = el.getAttribute("value").trim();
            if (!el.isSelected() && el.isEnabled() && actualValue.equals(value)) {
                el.click();
                break;
            }
        }
    }

    /**
     * this method send text to webElement with text box and hits enter
     *
     * @param element
     * @param text
     * @param key
     */
    public static void sendText(WebElement element, String text, Keys key) {
        element.clear();
        element.sendKeys(text, key);
    }

    /**
     * Method that checks if text is there and then selects it
     *
     * @param element
     * @param textToSelect
     */
    public static void selectDdValue(WebElement element, String textToSelect) {

        try {
            Select select = new Select(element);
            List<WebElement> options = select.getOptions();

            for (WebElement el : options) {
                if (el.getText().equals(textToSelect)) {
                    select.selectByVisibleText(textToSelect);
                    break;
                }
            }
        } catch (UnexpectedTagNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that selects value by index
     * @param element
     * @param index
     */
    public static void selectDdValue(WebElement element, int index) {

        try {
            Select select = new Select(element);
            int size = select.getOptions().size();

            if (size > index) {
                select.selectByIndex(index);
            }
        } catch (UnexpectedTagNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method checks do we have specific text in DropDownBox
     *
     * @param element - webElement of DropDownBox
     * @param text    - specific Text
     * @return - boolean
     */
    public static boolean isTextEnableInDropDown(WebElement element, String text) {
        boolean isTextEnable = false;
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        for (WebElement op : options) {
            if (op.getText().equals(text)) {
                isTextEnable = true;
            }
        }
        return isTextEnable;
    }

    /**
     * Methods that accept alerts and catches exception if alert is not present
     */
    public static void acceptAlert() {

        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();

        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methods that dismiss alerts and catches exception if alert is not present
     */
    public static void dismissAlert() {

        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();

        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methods that gets text of alert and catches exception if alert is not present
     * @return String alert text
     */
    public static String getAlertText() {

        String alertText = null;

        try {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }

        return alertText;
    }

    /**
     * Methods that sends text to alert and catches exception if alert is not
     * present
     *
     */
    public static void sendAlertText(String text) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    public static void switchToFrame(String nameOrId) {

        try {
            driver.switchTo().frame(nameOrId);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }

    public static void switchToFrame(WebElement element) {

        try {
            driver.switchTo().frame(element);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }

    public static void switchToFrame(int index) {

        try {
            driver.switchTo().frame(index);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method switches focus to child window
     */
    public static void switchToChildWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public static void jsScroll() {
        getJSExecutor().executeScript("window.scrollBy(0,200)");
    }

    public static void moveCursor(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public static String getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1970, 2019);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        String Day   = gc.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + gc.get(Calendar.DAY_OF_MONTH) : "" + gc.get(Calendar.DAY_OF_MONTH);
        String Month = (gc.get(Calendar.MONTH) + 1) < 10 ? "0" + (gc.get(Calendar.MONTH) + 1) : "" + (gc.get(Calendar.MONTH) + 1);
        return gc.get(Calendar.YEAR) + "-" + Month + "-" + Day;
    }
    public static String randomAlphabets() {
        Random random = new Random();
        int alphabetCount = 26;
        char[] alphabets = new char[alphabetCount];
        for (int i = 0; i < alphabetCount; i++) {
            alphabets[i] = (char) ('a' + i);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(alphabetCount);
            char randomAlphabet = alphabets[index];
            result.append(randomAlphabet);
        }
        return  result.toString() ;
    }

    public static void dynamicSearch(List<WebElement> listOfElement, String key, WebElement nextPageElement){
        boolean flag= true;
        while (flag){
            for (WebElement element: listOfElement){
                String text = element.getText();
                if(text.equalsIgnoreCase(key)){
                    click(element);
                    flag=false;
                    break;
                }
            }
            if(flag){
                click(nextPageElement);
            }
        }
    }

    public static void assertEquals (String expected, String actual){
        Assert.assertEquals(expected,actual);}


    /**
     * The next three methods are actually the same method that is being overloaded.
     * selectFromDropDown() selects an option from the dropdown list based on its visible text, value or index
     */
    public static void selectFromDropDownByVisibleText(WebElement element, String text) {
        try {
            Select sel = new Select(element);
            sel.selectByVisibleText(text);
        } catch (Exception e) {
            // Handle the exception as needed, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }

    public static void selectFromDropDownByValue(WebElement element, String value) {
        try {
            Select sel = new Select(element);
            sel.selectByValue(value);
        } catch (Exception e) {
            // Handle the exception as needed, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }

    public static void selectFromDropDownByIndex(WebElement element, int index) {
        try {
            Select sel = new Select(element);
            sel.selectByIndex(index);
        } catch (Exception e) {
            // Handle the exception as needed, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }

    public static void checkCheckbox(WebElement checkbox) {
        try {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception e) {
            // Handle the exception as needed, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }

    public static void uncheckCheckbox(WebElement checkbox) {
        try {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception e) {
            // Handle the exception as needed, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }

    public static boolean isCheckboxSelected(WebElement checkbox) {
        try {
            return checkbox.isSelected();
        } catch (Exception e) {
            // Handle the exception as needed, e.g., log it or throw a custom exception
            e.printStackTrace();
            return false;
        }
    }

    /**
     * selectCheckBoxFromMultipleOptionsd() method selects a checkbox from a list of multiple checkbox options
     * based on the given value, Iterates through the list of checkbox options.
     * if an option's value attribute matches the given value, and it is not already selected,
     * clicks on the checkbox to select it.
     */
    public static void selectCheckboxFromMultipleOptions(List<WebElement> options, String value){
        for (WebElement element: options){
            String option = element.getAttribute("value");
            if(!element.isSelected()&&option.equals(value)){
                click(element);
            }
        }
    }

    public static void selectRadioButton(List<WebElement> radioButtons, String value){
        for (WebElement radioButton: radioButtons){
            String option = radioButton.getAttribute("value");
            if(option.equals(value)&&radioButton.isEnabled()){
                click(radioButton);
            }
        }
    }





}