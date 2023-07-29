package api;

import io.cucumber.java.bs.A;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

public class ReqRest {

    public static void main(String[] args) {

        String url = "https://reqres.in/api/users/7";
        Response response = RestAssured.get(url);
        System.out.println(response.statusCode());
        System.out.println(response.asPrettyString());

        String email = response.jsonPath().get("data.email").toString();
        String firstName = response.jsonPath().get("data.first_name").toString();
        String lastName = response.jsonPath().get("data.last_name").toString();
        String avatar = response.jsonPath().get("data.avatar").toString();

        Assert.assertFalse(email.isEmpty());
        Assert.assertFalse(firstName.isEmpty());
        Assert.assertFalse(lastName.isEmpty());
        Assert.assertFalse(avatar.isEmpty());

        Assert.assertTrue(email.endsWith("reqres.in"));
        Assert.assertTrue(avatar.endsWith(".png") || avatar.contains(".jpg"));


    }
}
