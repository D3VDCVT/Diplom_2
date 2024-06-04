import api.UserApi;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import io.restassured.RestAssured;

import static models.Constants.*;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void deleteUser() {
        User login = new User (EMAIL, PASSWORD);
        UserApi userApi = new UserApi();
        Response response = userApi.checkUser(login);
        int statusCode = response.then().extract().statusCode();
        if (statusCode == 200) {
            String token = response.then().extract().path("accessToken");
            userApi.deleteUser(token).asString();
        }
    }
}
