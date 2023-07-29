package steps;

import com.github.javafaker.App;
import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.List;

public class BankAccountCreationSteps {

    int id1;
    int id2;

    Response response;

    @When("user hits the bank account creation API with correct body and token")
    public void user_hits_the_bank_account_creation_api_with_correct_body_and_token() {
        Faker faker = new Faker();
        String url = "/api/myaccount/bankaccount";
        RequestBody requestBody = new RequestBody();
        requestBody.setType_of_pay("BANK");
        requestBody.setBank_account_name(faker.gameOfThrones().character());
        requestBody.setDescription(faker.superhero().name());
        requestBody.setBalance(2300);
        APIRunner.runPOST(url, requestBody);
        id1 = APIRunner.getCustomResponse().getId();
    }
    @Then("user should hit get single account API")
    public void user_should_hit_get_single_account_api() {
        String url = "/api/myaccount/bankaccount/" + id1;
        APIRunner.runGET(url);
        id2 = APIRunner.getCustomResponse().getId();

    }



    @Then("user should verify that account was created and HTTP request should be {string}")
    public void user_should_verify_that_account_was_created_and_http_request_should_be(String string) {
        Assert.assertNotEquals(0, id1);
        Assert.assertNotEquals(0, id2);
        Assert.assertEquals("Account was not created", id1, id2);
        Assert.assertEquals(Integer.parseInt(string), APIRunner.getCustomResponse().getStatus());
    }





}
