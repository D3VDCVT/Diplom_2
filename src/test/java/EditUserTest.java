import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import models.UserResponse;
import org.junit.Assert;
import org.junit.Test;

import static models.Constants.*;

public class EditUserTest extends BaseTest {

    @DisplayName("Редактирование пользователя")
    @Test
    public void editUserWithAuthorizationTest () {
        String expectedEmail = EMAIL + "test";
        String expectedPassword = PASSWORD + "test";
        String expectedName = NAME + "test";
        User user = new User(EMAIL, PASSWORD, NAME);
        User editUser = new User(expectedEmail, expectedPassword, expectedName);
        UserApi userApi = new UserApi();
        UserResponse createUserResponse = userApi.createUser(user, 200);
        String token = createUserResponse.getAccessToken();
        UserResponse editUserResponse = userApi.editUserInfo(token, editUser, 200);
        userApi.deleteUser(token);
        Assert.assertTrue(editUserResponse.getSuccess());
        Assert.assertEquals(expectedEmail, editUserResponse.getUser().getEmail());
        Assert.assertEquals(expectedName, editUserResponse.getUser().getName());
    }
    @DisplayName("Редактирование пользователя, accessToken не указан")
    @Test
    public void editUserWithoutAuthorizationTest () {
        String expectedEmail = EMAIL + "test";
        String expectedPassword = PASSWORD + "test";
        String expectedName = NAME + "test";
        User user = new User(EMAIL, PASSWORD, NAME);
        User editUser = new User(expectedEmail, expectedPassword, expectedName);
        UserApi userApi = new UserApi();
        UserResponse createUserResponse = userApi.createUser(user, 200);
        String token = createUserResponse.getAccessToken();
        UserResponse editUserResponse = userApi.editUserInfoWithOutToken(editUser, 401);
        userApi.deleteUser(token);
        String expected = "You should be authorised";
        Assert.assertFalse(editUserResponse.getSuccess());
        Assert.assertEquals(expected, editUserResponse.getMessage());
    }

}
