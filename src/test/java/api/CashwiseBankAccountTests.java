package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import utilities.CashwiseAuthorization;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import utilities.Config;
import entities.CustomResponse;

import java.util.List;

public class CashwiseBankAccountTests {

    @Test
    public void getAllBankAccount(){
        String token = CashwiseAuthorization.getToken();
        Response response = RestAssured.given().auth().oauth2(token).
                get(Config.getValue("cashwiseApiUrl") + "/api/myaccount/bankaccount");

        List<String> bankAccountNames = response.jsonPath().get("bank_account_name");
        for(int i = 0; i < bankAccountNames.size(); i++){
            System.out.println(bankAccountNames.get(i));
        }

        List<String> bankAccountDescriptions = response.jsonPath().get("description");
        for(String description : bankAccountDescriptions){
            System.out.println(description);
        }

        List<String> typeOfPay = response.jsonPath().get("type_of_pay");
        for(String pay : typeOfPay){
            System.out.println(pay);
        }

        System.out.println();
        List<Float> balances = response.jsonPath().get("balance");
        for(Float balance : balances){
            System.out.println(balance);
        }
        System.out.println();
        List<String> ids = response.jsonPath().get("id");
        for(String id : ids){
            System.out.println(id);
        }

        System.out.println("Printing with for loop!");




        int size = response.jsonPath().getInt("$.size()");
        System.out.println(size);

        for(int i = 0; i < size; i++){
            String id = response.jsonPath().get("["+i+"].id");
            String description = response.jsonPath().get("["+i+"].description");
            String accountName = response.jsonPath().get("["+i+"].bank_account_name");
            Assert.assertFalse("ID is empty: " + i ,id.trim().isEmpty());
            Assert.assertFalse("Description is empty: " + i ,description.trim().isEmpty());
            Assert.assertFalse("Bank Account name is empty: " + i ,accountName.trim().isEmpty());

        }
    }

    @Test
    public void getAllAccounts() throws JsonProcessingException {

        String token = CashwiseAuthorization.getToken();
        String url = Config.getValue("cashwiseApiUrl") + "/api/myaccount/bankaccount";

        Response response = RestAssured.given().auth().oauth2(token).get(url);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse[] customResponse = mapper.readValue(response.asString(), CustomResponse[].class);

        String urlDeleteAccount = Config.getValue("cashwiseApiUrl") + "/api/myaccount/bankaccount/";
        for(int i = 0; i < customResponse.length; i++){
            if(customResponse[i].getBalance() <= 1000){
                System.out.println("Deleting " + customResponse[i].getBank_account_name());
                int bankAccountID = customResponse[i].getId();
                Response response1 = RestAssured.given().auth().oauth2(token).delete(urlDeleteAccount + bankAccountID);
                response1.prettyPrint();
                Assert.assertEquals(200, response1.statusCode());
            }
        }

    }
}
