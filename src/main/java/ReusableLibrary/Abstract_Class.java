package ReusableLibrary;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public class Abstract_Class {

    public static WebDriver driver = null;
    public static ExtentReports reports = null;
    public static ExtentTest printLog = null;

    //before suite annotation
    @BeforeSuite
    public void setDriver() throws IOException, InterruptedException {
        driver = Reusable_Actions.defineTheDriver();

        //set the path to te report that I want to use
        reports = new ExtentReports("src/main/java/HTML_Reports/Automation_Report.html");
    }//end of before suite

    //before method will start the log for your report and capture the test name
    @BeforeMethod
    public void captureTestName(Method methodName){
        printLog = reports.startTest(methodName.getName());
    }//end of before method

    //after method will end the logger (Print statement for the report) for individual test
    @AfterMethod
    public void endLogger(){
        reports.endTest(printLog);
    }

    @AfterSuite
    public void closeDriver(){
        //flush the logs for the report
        reports.flush();
        //quit the driver
        driver.quit();
    }//end of after suite

}