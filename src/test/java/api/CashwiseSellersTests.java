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
    public void createSeller() throws JsonProcessingException {
        Faker faker = new Faker();
        String name = faker.superhero().name();
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";

        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(name);
        requestBody.setEmail(name + "@gmail.com");
        requestBody.setPhone_number(faker.phoneNumber().subscriberNumber(9));
        requestBody.setAddress(faker.address().streetAddress());

        Response response = RestAssured.given().auth().oauth2(token).
                contentType(ContentType.JSON).body(requestBody).post(url);

        System.out.println(response.statusCode());
        response.prettyPrint();

////////////////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("Single User API");
        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        String sellerName = customResponse.getSeller_name();
        int sellerId = customResponse.getSeller_id();


        String url1 = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers/" + sellerId;
        Response response1 = RestAssured.given().auth().oauth2(token).get(url1);
        response1.prettyPrint();

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);

        Assert.assertEquals(200, response1.statusCode());
        Assert.assertEquals("Seller was not created" , sellerName, customResponse1.getSeller_name());



    }

    @Test
    public void createManySellers(){
        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers";
        Faker faker = new Faker();


        for(int i = 0; i < 15; i++){
            RequestBody requestBody = new RequestBody();
            requestBody.setCompany_name(faker.company().name());
            String name = faker.superhero().name();
            requestBody.setSeller_name(name);
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setPhone_number(faker.phoneNumber().subscriberNumber(9));
            requestBody.setAddress(faker.address().streetAddress());

            Response response = RestAssured.given().auth().oauth2(token).
                    contentType(ContentType.JSON).body(requestBody).post(url);

            System.out.println(response.statusCode());
        }
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

    @Test
    public void archiveSellers() throws JsonProcessingException {

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

        Map<String, Object> params1 = new HashMap<>();
        List<Integer> ids = new ArrayList<>();
        params1.put("archive", true);

        for(int i = 0; i < size; i++){
            if (customResponse.getResponses().get(i).getEmail().contains("hotmail.com")){
                int sellerId = customResponse.getResponses().get(i).getSeller_id();
                ids.add(sellerId);
            }
        }

        params1.put("sellersIdsForArchive", ids);
        String urlArchive = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";

        Response response1 = RestAssured.given().auth().oauth2(token).params(params1).post(urlArchive);
        System.out.println(response1.statusCode());
        response1.prettyPrint();
    }

    @Test
    public void updateSeller(){

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/sellers/1";

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("Uliandro LLC.");
        requestBody.setSeller_name("Ulibek");
        requestBody.setEmail("delaputa@gmail.com");
        requestBody.setPhone_number("123123123");
        requestBody.setAddress("Kadamjay");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).put(url);
        System.out.println(response.statusCode());
        response.prettyPrint();

    }



}
