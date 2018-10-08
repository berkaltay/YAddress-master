package methods;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class ReusableMethods {


    //Useful methods to help tidy code set-up


    //Converting raw-text response to Json response
    public static JsonPath rawtoJson(Response r)

    {

        String response = r.asString();
        JsonPath x = new JsonPath(response);
        return x;
    }


    //Switch case structure to give detailed information about returning ErrorCode
    public static void errorCodeInfo (Integer errorCode){
        switch (errorCode){
            case 0:
                System.out.println("Error Code in Response: 'No error'");
                break;
            case 1:
                System.out.println("System error. Operator's attention required. See Error Message for more details.");
                break;
            case 2:
                System.out.println("Input address was supplied in an invalid format.");
                break;
            case 3:
                System.out.println("Specified street was not found in the ZIP code provided or in any ZIP code of the city-state.");
                break;
            case 4:
                System.out.println("City is not found in the state.");
                break;
            case 5:
                System.out.println("Ambiguous street name. More than one street matches the search address with equal accuracy.");
                break;
            case 6:
                System.out.println("YAddress installation is corrupted. Reinstall YAddress.");
                break;
            case 7:
                System.out.println("YAddress data edition has expired. Contact www.yaddress.net to obtain current edition.");
                break;
            case 8:
                System.out.println("No such house number in the street.");
                break;
        }

    }

}
