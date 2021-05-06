import PageObjects.Base_Class;
import ReusableLibrary.Abstract_Class;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Twitter_UseCase_1and2 extends Abstract_Class {


    String consumerKey = "un4Ns7LCKzQfPPL9yz6iWinO7";
    String consumerSecret = "gaVThRj5xo7OKp1Vxg55uoL20I7REpIpxBZswzvgD663pP4ed2";
    String accessToken = "804970976052203520-TrLq0dF9dGdMIUqHo3RSmlB37RjveMp";
    String tokenSecret = "dfhvqVGXk1Wnb1jrMHevm0Dc4DHOtqfyw7kHMG2yCJcds";
    String Tweetid;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up

    @Test(priority = 1)
    public void useCase1() throws InterruptedException {
        driver.navigate().to("https://twitter.com");
        Base_Class.twitterHomePage().logInButton();
        Thread.sleep(2000);
        Base_Class.twitterHomePage().username("Tenzing_JFC");
        Base_Class.twitterHomePage().password("Wherestibet1");
        Base_Class.twitterHomePage().finalizeLogIn();
        Thread.sleep(2000);
        Base_Class.writeTweet().tweet();
        Thread.sleep(2000);
        Base_Class.writeTweet().writeTweet(" PERM IS A PIECE OF SCHMIDT");
        Base_Class.writeTweet().tweetButton();
        Thread.sleep(2000);

        //API verify tweet is created
        Response Resp =
                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        String getTweet = Resp.asString();
        JsonPath js = new JsonPath(getTweet);
        String text1 = js.get("text[0]").toString();
        System.out.println("The recent tweet text is " + text1);

        if (text1.equals("PERM IS A PIECE OF SCHMIDT")) {
            System.out.println("Tweet has been posted");
            Abstract_Class.printLog.log(LogStatus.PASS, "Tweet has been posted");
        } else {
            System.out.println("Tweet has not been posted");
            Abstract_Class.printLog.log(LogStatus.FAIL, "Tweet has not been posted");
        }
        //get ID of new tweet
        Tweetid = (js.get("id[0]")).toString();
        System.out.println("Id of newly created tweet is " + Tweetid);

        //delete tweet using API

        given().
                auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("id", Tweetid)
                .when().post("destroy.json")
                .then();
        System.out.println("Recent twitter message with id '" + Tweetid + "' has been deleted");
        Thread.sleep(2000);

        //refresh page and verify the tweet isn't present

        Response Resp2 =
                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        driver.navigate().refresh();
        Thread.sleep(3000);
        String getTweet2 = Resp2.asString();
        JsonPath jd = new JsonPath(getTweet2);
        String text2 = jd.get("text[0]").toString();
        if (text2.equals("PERM IS A PIECE OF SCHMIDT")) {
            System.out.println("Tweet has not been deleted");
            printLog.log(LogStatus.FAIL, "Tweet has not been deleted");
        } else {
            System.out.println("Tweet has been deleted");
            printLog.log(LogStatus.PASS, "Tweet has been deleted");
        }
        Thread.sleep(1000);
        Base_Class.twitterHomePage().profileOptions();
        Base_Class.twitterHomePage().logOut();
        Base_Class.twitterHomePage().confirmLogOut();
        Thread.sleep(5000);
    }

    @Test(priority = 2)
    public void useCase2() throws InterruptedException {
        String postTweet = null;

        for (int i = 0; i < 20; i++) {
            postTweet = "Message #" + (i + 1);
            Response Resp = given()
                    .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                            queryParam("status", postTweet).
                            when().post("update.json")
                    .then().extract().response();
            if (Resp.statusCode() == 200) {
                System.out.println("Status code is 200 and successful. The following tweet has been posted: " + postTweet + ".");
                printLog.log(LogStatus.PASS, "Status code is 200 and successful. The following tweet has been posted: " + postTweet + ".");
            } else {
                System.out.println("Status code not 200." + Resp.statusCode());
                printLog.log(LogStatus.FAIL, "Status code not 200." + Resp.statusCode());
            }
        }

        driver.navigate().to("https://twitter.com");
        Base_Class.twitterHomePage().logInButton();
        Thread.sleep(2000);
        Base_Class.twitterHomePage().username("Tenzing_JFC");
        Base_Class.twitterHomePage().password("Wherestibet1");
        Base_Class.twitterHomePage().finalizeLogIn();
        Thread.sleep(2000);

        Response Resp =
                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        String getTweet = Resp.asString();
        JsonPath js = new JsonPath(getTweet);
        String text1 = js.get("text[0]").toString();
        System.out.println("The recent tweet text is " + text1);
        String firstTweet = (js.get("id[0]")).toString();
        System.out.println("Id of first tweet is " + firstTweet);
        String text2 = js.get("text[1]").toString();
        System.out.println("The recent tweet text is " + text2);
        String secondTweet = (js.get("id[1]")).toString();
        System.out.println("Id of second tweet is " + secondTweet);

        System.out.println("The most recent tweet is " + text1 + ". with the following ID: " + firstTweet + ".");
        printLog.log(LogStatus.INFO, "The most recent tweet is " + text1 + ". with the following ID: " + firstTweet + ".");
        System.out.println("The most recent tweet is " + text2 + ". with the following ID: " + secondTweet + ".");
        printLog.log(LogStatus.INFO, "The most recent tweet is " + text2 + ". with the following ID: " + secondTweet + ".");

        Response Resp2 =
                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();
        for (int i = 0; i < 20; i++) {
            String deleteTweet = Resp2.asString();
            JsonPath jd = new JsonPath(deleteTweet);
            Tweetid = (jd.get("id[" + i  + "]")).toString();

            given().
                    auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                    queryParam("id", Tweetid)
                    .when().post("destroy.json")
                    .then();
            System.out.println("Tweet message " + (i+1) + " with ID" + Tweetid + " has been deleted.");
            Thread.sleep(2000);
        }//end of loop

        Response Resp3 =
                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        driver.navigate().refresh();
        Thread.sleep(3000);
        String getTweet2 = Resp3.asString();
        JsonPath jv = new JsonPath(getTweet2);
        String tweet1 = jv.get("text[0]").toString();
        System.out.println("The recent tweet text is " + tweet1);
        String tweet2 = jv.get("text[1]").toString();
        System.out.println("The recent tweet text is " + tweet2);
        if (tweet1.equals("Message #20")){
            System.out.println("Tweet has not been deleted");
            printLog.log(LogStatus.FAIL, "Tweet has not been deleted API");
        } else {
            System.out.println("Tweet has been deleted");
        }//end of if/else
        printLog.log(LogStatus.PASS, "Tweet has been deleted API");
        if (tweet2.equals("Message #19")) {
            System.out.println("Tweet has not been deleted");
            printLog.log(LogStatus.FAIL, "Tweet has not been deleted API");
        } else {
            System.out.println("Tweet has been deleted");
            printLog.log(LogStatus.PASS, "Tweet has been deleted API");
        }//end of if/else
        Thread.sleep(1000);
        Base_Class.twitterHomePage().profileOptions();
        Base_Class.twitterHomePage().logOut();
        Base_Class.twitterHomePage().confirmLogOut();

    }//end of test 2
}
