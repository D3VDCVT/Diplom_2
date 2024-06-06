package api;

import io.qameta.allure.Step;
import models.GetOrderResponse;
import models.Ingredients;
import models.OrderModel;

import static io.restassured.RestAssured.given;
import static models.Constants.*;

public class OrderApi {
    @Step("Send GET request get ingredients to /api/ingredients")
    public Ingredients getIngredients(int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .get(GET_INGREDIENTS)
                .then()
                .statusCode(expectedCode)
                .extract().as(Ingredients.class);
    }

    @Step("Send POST request create order to /api/orders")
    public OrderModel createOrder(String token, OrderModel order, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .header(AUTHORIZATION, token)
                .body(order)
                .post(CREATE_ORDER)
                .then()
                .statusCode(expectedCode)
                .extract().as(OrderModel.class);
    }

    @Step("Send POST request create order to /api/orders without accessToken")
    public OrderModel createOrderWithOutToken(OrderModel order, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .body(order)
                .post(CREATE_ORDER)
                .then()
                .statusCode(expectedCode)
                .extract().as(OrderModel.class);
    }

    @Step("Send GET request get order to /api/orders")
    public GetOrderResponse getOrder(String token, int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .header(AUTHORIZATION, token)
                .get(GET_ORDER)
                .then()
                .statusCode(expectedCode)
                .extract().as(GetOrderResponse.class);
    }

    @Step("Send GET request get order to /api/orders without accessToken")
    public GetOrderResponse getOrderWithOutToken(int expectedCode) {
        return given()
                .header(HEADER_TYPE,HEADER_JSON)
                .get(GET_ORDER)
                .then()
                .statusCode(expectedCode)
                .extract().as(GetOrderResponse.class);
    }
}
