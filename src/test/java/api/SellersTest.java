package api;

import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.bs.A;
import org.junit.Assert;
import org.junit.Test;
import utilities.APIRunner;
import utilities.CashwiseAuthorization;

import java.util.HashMap;

public class SellersTest {

    @Test
    public void getSeller(){

        String path = "/api/myaccount/sellers/1088";
        APIRunner.runGET(path);
        System.out.println(APIRunner.getCustomResponse().getSeller_name());
        System.out.println(APIRunner.getCustomResponse().getSeller_id());
        System.out.println(APIRunner.getCustomResponse().getResponseBody());
    }

    @Test
    public void getAllSellers(){

        String path = "/api/myaccount/sellers";

        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 20);
        APIRunner.runGET(path, params);

        for(CustomResponse response : APIRunner.getCustomResponse().getResponses()){
            System.out.println(response.getCompany_name());
        }
    }

    @Test
    public void createSeller(){

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("Petro");
        requestBody.setSeller_name("Dungan");
        requestBody.setEmail("dungan@gmail.com");
        requestBody.setPhone_number("123123123");
        requestBody.setAddress("Duncan Street 13");
        APIRunner.runPOST("/api/myaccount/sellers", requestBody);
        System.out.println(APIRunner.getCustomResponse().getResponseBody());
    }

    @Test
    public void singleSellerCreation(){
        String pathForPost = "/api/myaccount/sellers/";
        Faker faker = new Faker();
        String companyName = faker.company().name();
        String sellerName = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(companyName);
        requestBody.setSeller_name(sellerName);
        requestBody.setEmail(email);
        requestBody.setPhone_number(phoneNumber);

        APIRunner.runPOST(pathForPost, requestBody);
        int sellerID = APIRunner.getCustomResponse().getSeller_id();


        String pathForGet = "/api/myaccount/sellers/" + sellerID;
        APIRunner.runGET(pathForGet);

        Assert.assertEquals("Seller is not created", companyName, APIRunner.getCustomResponse().getCompany_name());
        Assert.assertEquals(sellerName, APIRunner.getCustomResponse().getSeller_name());
        Assert.assertEquals(email, APIRunner.getCustomResponse().getEmail());
        Assert.assertEquals(phoneNumber, APIRunner.getCustomResponse().getPhone_number());
    }
}
