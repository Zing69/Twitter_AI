package PageObjects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions_PageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Abstract_Class {

    ExtentTest printLog;
    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.printLog = Abstract_Class.printLog;
    }//end of constructor


    @FindBy(xpath = "//span[text()='Log in']")
    WebElement logInButton;

    @FindBy(xpath = "//input[@name='session[username_or_email]']")
    WebElement username;

    @FindBy(xpath = "//input[@name='session[password]']")
    WebElement password;

    @FindBy(xpath = "//span[text()='Log in']")
    WebElement finalizeLogIn;

    @FindBy(xpath = "//div[@aria-label='Account menu']")
    WebElement profileOptions;

    @FindBy(xpath = "//*[@data-testid='AccountSwitcher_Logout_Button']")
    WebElement logOut;

    @FindBy(xpath = "//*[@data-testid='confirmationSheetConfirm']")
    WebElement confirmLogOut;

    public void logInButton(){
        Reusable_Actions_PageObjects.clickOnElement(driver,logInButton,printLog,"Log in button");
    }
    public void username(String userValue){
        Reusable_Actions_PageObjects.sendKeysMethod(driver,username,userValue,printLog,"Username");
    }
    public void password(String userValue){
        Reusable_Actions_PageObjects.sendKeysMethod(driver,password,userValue,printLog,"Password");
    }
    public void finalizeLogIn(){
        Reusable_Actions_PageObjects.clickOnElement(driver,finalizeLogIn,printLog,"Log in");
    }
    public void profileOptions(){
        Reusable_Actions_PageObjects.clickOnElement(driver,profileOptions,printLog,"Profile Options");
    }
    public void logOut(){
        Reusable_Actions_PageObjects.clickOnElement(driver,logOut,printLog,"Logout");
    }
    public void confirmLogOut(){
        Reusable_Actions_PageObjects.clickOnElement(driver,confirmLogOut,printLog,"Confirm Log Out");
    }
}
