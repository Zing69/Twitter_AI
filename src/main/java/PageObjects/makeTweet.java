package PageObjects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions;
import ReusableLibrary.Reusable_Actions_PageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class makeTweet extends Abstract_Class {
    ExtentTest printLog;
    public makeTweet(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.printLog = Abstract_Class.printLog;
    }

    @FindBy(xpath = "//*[@aria-label='Tweet']")
    WebElement tweet;

    @FindBy(xpath = "//div[@class='public-DraftStyleDefault-block public-DraftStyleDefault-ltr']")
    WebElement writeTweet;

    @FindBy(xpath =  "//div[@data-testid='tweetButton']")
    WebElement tweetButton;



    public void tweet(){
        Reusable_Actions_PageObjects.clickOnElement(driver,tweet,printLog,"Create Tweet Button");
    }
    public void writeTweet(String userValue){
        Reusable_Actions_PageObjects.sendKeysMethod(driver,writeTweet,userValue,printLog,"Write Tweet");
    }
    public void tweetButton(){
        Reusable_Actions_PageObjects.clickOnElement(driver,tweetButton,printLog,"Finalize Tweet");
    }
}
