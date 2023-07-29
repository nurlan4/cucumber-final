package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class APIRunner {


    private static CustomResponse customResponse;
    public static void runGET(String path){

        String token = CashwiseAuthorization.getToken();
        Response response = RestAssured.given().auth().oauth2(token).get(Config.getValue("cashwiseApiUrl") + path);
        System.out.println("Status code: " + response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            customResponse.setStatus(response.statusCode());
            customResponse.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("Probably list response");
        }
    }

    public static CustomResponse getCustomResponse(){
        return customResponse;
    }

    public static void runGET(String path, HashMap<String,Object> params){

        String token = CashwiseAuthorization.getToken();
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(Config.getValue("cashwiseApiUrl") + path);
        System.out.println("Status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            customResponse.setStatus(response.statusCode());
            customResponse.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("Probably list response");
        }
    }

    public static void runPOST(String path , RequestBody requestBody){
        String token = CashwiseAuthorization.getToken();

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).
                body(requestBody).post(Config.getValue("cashwiseApiUrl") + path);
        System.out.println("Status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            customResponse.setStatus(response.statusCode());
            customResponse.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("Probably list response");
        }
    }

    public static void runPUT(String path, RequestBody requestBody){
        String token = CashwiseAuthorization.getToken();

        Response response = RestAssured.given().auth().oauth2(token).
                contentType(ContentType.JSON).body(requestBody).put(Config.getValue("cashwiseApiUrl") + path);
        System.out.println("Status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            customResponse.setStatus(response.statusCode());
            customResponse.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("Probably list response");
        }
    }
}
