package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import utilities.CashwiseAuthorization;
import utilities.Config;
import entities.CustomResponse;
import entities.RequestBody;

public class CashwiseCategoriesTests {




    @Test
    public void getIncomeCategories(){

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/categories/income";

        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println(response.statusCode());
        response.prettyPrint();
    }

    @Test
    public void getExpenseCategories(){

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/categories/expense";

        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println(response.statusCode());
        response.prettyPrint();
    }
}
