package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashwiseAuthorization;
import utilities.Config;
import entities.CustomResponse;
import entities.RequestBody;

import java.util.*;

public class CashwiseSellersTests {

    @Test
    public void getAllSellers(){

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 5);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println(response.statusCode());
        response.prettyPrint();
    }

    @Test
    public void getSingleSeller(){

        String token = CashwiseAuthorization.getToken();
        int id = 1;

        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers/" + id;

        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println(response.statusCode());
        response.prettyPrint();

        String sellerName = response.jsonPath().get("seller_name");
        System.out.println(sellerName);
        Assert.assertTrue("Seller name is not Ular", sellerName.equals("Ular"));

    }


    @Test
    public void getAllSellersEmail() throws JsonProcessingException {

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 60);
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();
        for(int i = 0; i < size; i++){
            System.out.println(customResponse.getResponses().get(i).getEmail());
        }
    }




}
