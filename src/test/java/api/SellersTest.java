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

}
