import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import models.UserResponse;
import org.junit.Assert;
import org.junit.Test;

import static models.Constants.*;

public class LoginUserTest extends BaseTest{

    @DisplayName("Логин пользователя")
    @Test
    public void loginUserTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        User login = new User(EMAIL,PASSWORD);
        UserApi userApi = new UserApi();
        userApi.createUser(user, 200);
        UserResponse loginResponse = userApi.loginUser(login, 200);
        Assert.assertTrue(loginResponse.getSuccess());
        Assert.assertNotNull(loginResponse.getAccessToken());
        Assert.assertNotNull(loginResponse.getRefreshToken());
        Assert.assertEquals(EMAIL,loginResponse.getUser().getEmail());
        Assert.assertEquals(NAME,loginResponse.getUser().getName());
    }

    @DisplayName("Логин пользователя, неверный email")
    @Test
    public void loginUserWithWrongEmailTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        User login = new User(EMAIL + "Test",PASSWORD);
        UserApi userApi = new UserApi();
        userApi.createUser(user, 200);
        UserResponse loginResponse = userApi.loginUser(login, 401);
        String expected = "email or password are incorrect";
        Assert.assertFalse(loginResponse.getSuccess());
        Assert.assertEquals(expected, loginResponse.getMessage());
    }

    @DisplayName("Логин пользователя, неверный password")
    @Test
    public void loginUserWithWrongPasswordTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        User login = new User(EMAIL,PASSWORD + "Test");
        UserApi userApi = new UserApi();
        userApi.createUser(user, 200);
        UserResponse loginResponse = userApi.loginUser(login, 401);
        String expected = "email or password are incorrect";
        Assert.assertFalse(loginResponse.getSuccess());
        Assert.assertEquals(expected, loginResponse.getMessage());
    }
}
