package PageObjects;

import ReusableLibrary.Abstract_Class;

public class Base_Class extends Abstract_Class {

    public static HomePage twitterHomePage(){
        HomePage twitterHomePage = new HomePage(driver);
        return twitterHomePage;
    }

    public static makeTweet writeTweet(){
        makeTweet writeTweet = new makeTweet(driver);
        return writeTweet;
    }

}//end of java
