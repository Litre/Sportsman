package net.thucydides.sportsman;

import com.jayway.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import steps.SportsmanTestSteps;
import org.apache.commons.lang.RandomStringUtils;
import org.jbehave.core.annotations.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by ltre on 1/30/2016.
 */
public class APITest {

    private static final String loginUrl="http://streamtv.net.ua/base/php/login.php";

    private static final String request="http://streamtv.net.ua/base/php/wrestler/create.php";

    private static final String  deleteUrl ="http://streamtv.net.ua/base/php/wrestler/delete.php?id=";

    @Steps
    SportsmanTestSteps searchSteps;


    public String  loginAPI() throws JSONException  {
        Map<String, String> credentials=new HashMap<String, String>();
        credentials.put("username","auto");
        credentials.put("password","test");
        JSONObject jsonObject = new JSONObject(credentials);
        Response response = given().contentType("application/json").body(jsonObject.toString()).
                when().post(loginUrl);
        String bodyResponse=response.body().asString();
        JSONObject JSONResponseBody = new JSONObject( bodyResponse);
        System.out.println("JSONResponseBody: " +JSONResponseBody);
        String result = JSONResponseBody.getString("result");
        assertEquals(result, "true");
        String sessoinId=response.cookie("PHPSESSID");
        return sessoinId;
    }


    @Given("create user through API")
    public void createUser(){
        Map<String, String> sportsman =new HashMap<String, String>();
        sportsman.put("lname", RandomStringUtils.randomAlphabetic(8));
        sportsman.put("fname", RandomStringUtils.randomAlphabetic(6));
        sportsman.put("mname",  RandomStringUtils.randomAlphabetic(10));
        sportsman.put("dob","25-05-1994");
        sportsman.put("style","1");
        sportsman.put("region1","11");
        sportsman.put("fst1","2");
        sportsman.put("fst2","6");
        sportsman.put("expires","2015");
        sportsman.put("lictype","1");
        sportsman.put("card_state","1");
        JSONObject jsonObject = new JSONObject(sportsman);
        try {
            Response response = given().contentType(JSON).cookie("PHPSESSID", loginAPI()).body(jsonObject.toString()).
                    when().post(request);
            response.then().statusCode(200);
            JSONObject JSONResponseBody = new JSONObject( response.body().asString());
            String userId=getUserId(JSONResponseBody);
            TestUtil.putInSession("userId", userId);
        }
        catch (JSONException ex){
            System.out.println("JSON Exception");
        }
    }

    @Then ("delete user through API")
    public void delete ()throws JSONException{
        String userId=(String) TestUtil.getFromSession("userId");
            Response response = given().cookie("PHPSESSID", loginAPI()).
                    when().delete(deleteUrl+userId);
            response.then().statusCode(200);
            assertThat(response.asString(), not(containsString("Invalid query")));
        }

    public String getUserId(JSONObject body) throws JSONException{
        String userid=body.get("id").toString();
        return userid;
    }

}

