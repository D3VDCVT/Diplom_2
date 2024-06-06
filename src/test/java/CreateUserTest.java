import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import models.UserResponse;
import models.User;
import org.junit.Assert;
import org.junit.Test;

import static models.Constants.*;
public class CreateUserTest extends BaseTest {

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUserTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        UserApi userApi = new UserApi();
        UserResponse userResponse = userApi.createUser(user, 200);
        Assert.assertTrue(userResponse.getSuccess());
        Assert.assertEquals(EMAIL, userResponse.getUser().getEmail());
        Assert.assertEquals(NAME, userResponse.getUser().getName());
        Assert.assertNotNull(userResponse.getAccessToken());
        Assert.assertNotNull(userResponse.getRefreshToken());
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Test
    public void createUserExistingTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        UserApi userApi = new UserApi();
        userApi.createUser(user, 200);
        UserResponse createUserExistingResponse = userApi.createUser(user, 403);
        String expected = "User already exists";
        Assert.assertFalse(createUserExistingResponse.getSuccess());
        Assert.assertEquals(expected,createUserExistingResponse.getMessage());
    }

    @DisplayName("Создание пользователя, поле email не заполнено")
    @Test
    public void createUserWithoutEmailTest() {
        User user = new User(null, PASSWORD, NAME);
        UserApi userApi = new UserApi();
        UserResponse userResponse = userApi.createUser(user, 403);
        String expected = "Email, password and name are required fields";
        Assert.assertFalse(userResponse.getSuccess());
        Assert.assertEquals(expected, userResponse.getMessage());
    }

    @DisplayName("Создание пользователя, поле password не заполнено")
    @Test
    public void createUserWithoutPasswordTest() {
        User user = new User(EMAIL, null, NAME);
        UserApi userApi = new UserApi();
        UserResponse userResponse = userApi.createUser(user, 403);
        String expected = "Email, password and name are required fields";
        Assert.assertFalse(userResponse.getSuccess());
        Assert.assertEquals(expected, userResponse.getMessage());
    }

    @DisplayName("Создание пользователя, поле name не заполнено")
    @Test
    public void createUserWithoutNameTest() {
        User user = new User(EMAIL, PASSWORD, null);
        UserApi userApi = new UserApi();
        UserResponse userResponse = userApi.createUser(user, 403);
        String expected = "Email, password and name are required fields";
        Assert.assertFalse(userResponse.getSuccess());
        Assert.assertEquals(expected, userResponse.getMessage());
    }

}
