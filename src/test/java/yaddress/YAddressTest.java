package yaddress;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import methods.ReusableMethods;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class YAddressTest {

    //property instantiated
    Properties prop = new Properties();

    //TestNG annotation to run before test
    @BeforeTest

    //configurations for webservice timeout and property file
    public void configTimeOutandProperties() throws IOException {


        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\env.properties");
        prop.load(fis);

        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout", 15000).
                setParam("http.socket.timeout", 15000).
                setParam("http.connection-manager.timeout", 15000));
        System.out.println("Preparing connection timeout");
    }

    //TestNG annotation to run the test
    @Test

    //test of webservice for GET method with assertions
    public void RequestParametersTest() {

        //query strings as variables for tidying code
        String address1 ="506 Fourth Avenue Unit 1";
        String address2 ="ASBURY PARK, NJ 07712-6086";

        //info of test initalization can be seen on console
        System.out.println("Running test automation");

        //query string paremeters assigned to a variable with response
        Response res = given().
                param("AddressLine1", address1).
                param("AddressLine2", address2).
                header("Content-Type", "Accept: application/json").
                expect().
                when().
                //baseURI from property file
                get(prop.getProperty("HOST")).
        then().
                //Assertions here
                assertThat().
                        //checks if web service functioning with status code 200
                statusCode(200).and().
                        //checks if it's returning in JSON format
                contentType(ContentType.JSON).and().
                        //checks if ErrorCode returns zero
                body("ErrorCode", equalTo(0)).and().
                        //checks if Server of WebService is as defined
                header("Server", "Microsoft-IIS/8.5").
        extract().
                response();

        //writes the response in a formatted way to console
        res.prettyPrint();

        //raw to Json to be able to parse ErrorCode correctly which is Integer
        JsonPath js =   ReusableMethods.rawtoJson(res);
        Integer errorCode = js.get("ErrorCode");
        //checks for ErrorCode and gives detailed information using Parameter Reference
        ReusableMethods.errorCodeInfo(errorCode);

        //Info for console
        System.out.println("Test has run");

    }

}
