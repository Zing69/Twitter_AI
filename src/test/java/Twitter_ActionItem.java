import PageObjects.Base_Class;
import ReusableLibrary.Abstract_Class;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Twitter_ActionItem extends Abstract_Class {


    String consumerKey = "un4Ns7LCKzQfPPL9yz6iWinO7";
    String consumerSecret = "gaVThRj5xo7OKp1Vxg55uoL20I7REpIpxBZswzvgD663pP4ed2";
    String accessToken = "804970976052203520-TrLq0dF9dGdMIUqHo3RSmlB37RjveMp";
    String tokenSecret = "dfhvqVGXk1Wnb1jrMHevm0Dc4DHOtqfyw7kHMG2yCJcds";
    String Tweetid;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up

    @Test
    public void createTweet() throws InterruptedException {
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

        driver.navigate().refresh();
        Thread.sleep(3000);
        String text1 = driver.findElements(By.xpath("//div[@class='css-901oao r-1fmj7o5 r-1qd0xha r-a023e6 r-16dba41 r-rjixqe r-bcqeeo r-bnwqim r-qvutc0']")).get(0).getText();
        if (text1.equals("PERM IS A PIECE OF SCHMIDT")){
            System.out.println("Tweet has been posted");
            printLog.log(LogStatus.PASS,"Tweet has been posted");
        } else {
            System.out.println("Tweet has not been posted");
            printLog.log(LogStatus.FAIL,"Tweet has not been posted");
        }

        //API verify tweet ID
        Response Resp =
                given().
                        auth().oauth(consumerKey,consumerSecret,accessToken,tokenSecret).
                        queryParam("screen_name","@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        String getTweet = Resp.asString();
        JsonPath js = new JsonPath(getTweet);
        Tweetid = (js.get("id[0]")).toString();
        System.out.println("Id of newly created tweet is " + Tweetid);

        //delete tweet using API
        given().
                auth().oauth(consumerKey,consumerSecret,accessToken,tokenSecret).
                queryParam("id", Tweetid)
                .when().post("destroy.json")
                .then();
                System.out.println("Recent twitter message with id '"+Tweetid+"' has been deleted");
        Thread.sleep(2000);

        //refresh page and verify the tweet isn't present

        Response Resp2 =
                given().
                        auth().oauth(consumerKey,consumerSecret,accessToken,tokenSecret).
                        queryParam("screen_name","@Tenzing_JFC")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        driver.navigate().refresh();
        Thread.sleep(3000);
        String getTweet2 = Resp2.asString();
        JsonPath jd = new JsonPath(getTweet2);
        String text2 = jd.get("text[0]").toString();
        if (text2.equals("PERM IS A PIECE OF SCHMIDT")){
            System.out.println("Tweet has not been deleted");
            Abstract_Class.printLog.log(LogStatus.PASS, "Tweet has not been deleted");
        } else {
            System.out.println("Tweet has been deleted");
            Abstract_Class.printLog.log(LogStatus.FAIL, "Tweet has been deleted");
        }
        Thread.sleep(1000);
        Base_Class.twitterHomePage().profileOptions();
        Base_Class.twitterHomePage().logOut();
        Base_Class.twitterHomePage().confirmLogOut();
    }

}//end of java
