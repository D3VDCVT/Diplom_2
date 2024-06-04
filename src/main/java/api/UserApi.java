package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserResponse;
import models.User;

import static io.restassured.RestAssured.given;
import static models.Constants.*;
public class UserApi {
    @Step("Send POST request create user to /api/auth/register")
    public UserResponse createUser(User user, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .body(user)
                .post(CREATE_USER)
                .then()
                .statusCode(expectedCode)
                .extract().as(UserResponse.class);
    }

    @Step("Send POST request login user to /api/auth/login")
    public UserResponse loginUser(User user, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .body(user)
                .post(LOGIN_USER)
                .then()
                .statusCode(expectedCode)
                .extract().as(UserResponse.class);
    }

    @Step("Send POST request login user to /api/auth/login without extracting at class")
    public Response checkUser(User user) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .body(user)
                .post(LOGIN_USER);
    }

    @Step("Send GET request get user info to /api/auth/user")
    public UserResponse getUserInfo(String token, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .header(AUTHORIZATION, token)
                .get(GET_USER_INFO)
                .then()
                .statusCode(expectedCode)
                .extract().as(UserResponse.class);
    }

    @Step("Send PATH request edit user info to /api/auth/user")
    public UserResponse editUserInfo (String token, User user, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .header(AUTHORIZATION, token)
                .body(user)
                .patch(PATH_USER_INFO)
                .then()
                .statusCode(expectedCode)
                .extract().as(UserResponse.class);
    }

    @Step("Send PATH request edit user info to /api/auth/user without accessToken")
    public UserResponse editUserInfoWithOutToken (User user, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .body(user)
                .patch(PATH_USER_INFO)
                .then()
                .statusCode(expectedCode)
                .extract().as(UserResponse.class);
    }

    @Step("Send DELETE request user to /api/auth/user without extracting at class")
    public Response deleteUser (String token) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .header(AUTHORIZATION, token)
                .delete(DELETE_USER);
    }
}
