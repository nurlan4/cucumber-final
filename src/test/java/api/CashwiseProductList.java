package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import utilities.CashwiseAuthorization;
import utilities.Config;

import java.util.HashMap;
import java.util.Map;

public class CashwiseProductList {

    @Test
    public void getAllProducts(){

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/products";

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 5);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println(response.statusCode());
        response.prettyPrint();

        String product_title = response.jsonPath().get("responses[0].product_title");
        System.out.println(product_title);
    }
}
