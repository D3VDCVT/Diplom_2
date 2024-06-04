
import api.OrderApi;
import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import models.Ingredients;
import models.OrderModel;
import models.User;
import models.UserResponse;
import org.junit.Assert;
import org.junit.Test;

import static models.Constants.*;

public class CreateOrderTest extends BaseTest {

    @DisplayName("Создание заказа с авторизацией")
    @Test
    public void createOrderTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        UserApi userApi = new UserApi();
        OrderApi orderApi = new OrderApi();

        UserResponse createUserResponse = userApi.createUser(user, 200);
        String token = createUserResponse.getAccessToken();

        Ingredients ingredients = orderApi.getIngredients(200);
        String firstIngredient = ingredients.getData().get(0).get_id();
        String secondIngredient = ingredients.getData().get(1).get_id();
        String[] order = new String[] {firstIngredient, secondIngredient};
        OrderModel createOrder = new OrderModel(order);

        OrderModel createOrderRequest = orderApi.createOrder(token, createOrder, 200);
        Assert.assertTrue(createOrderRequest.getSuccess());
        Assert.assertNotNull(createOrderRequest.getName());
        Assert.assertNotNull(createOrderRequest.getOrder().getNumber());
    }

    @DisplayName("Создание заказа без авторизации")
    @Test
    public void createOrderWithoutAuthorizationTest() {

        OrderApi orderApi = new OrderApi();

        Ingredients ingredients = orderApi.getIngredients(200);
        String firstIngredient = ingredients.getData().get(0).get_id();
        String secondIngredient = ingredients.getData().get(1).get_id();
        String[] order = new String[] {firstIngredient, secondIngredient};
        OrderModel createOrder = new OrderModel(order);

        OrderModel createOrderRequest = orderApi.createOrderWithOutToken(createOrder, 200);
        Assert.assertTrue(createOrderRequest.getSuccess());
        Assert.assertNotNull(createOrderRequest.getName());
        Assert.assertNotNull(createOrderRequest.getOrder().getNumber());
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    public void CreateOrderWithoutIngredientsTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        UserApi userApi = new UserApi();
        OrderApi orderApi = new OrderApi();

        UserResponse createUserResponse = userApi.createUser(user, 200);
        String token = createUserResponse.getAccessToken();
        String[] order = new String[] {};
        OrderModel createOrder = new OrderModel(order);

        OrderModel createOrderRequest = orderApi.createOrder(token, createOrder, 400);

        String expected = "Ingredient ids must be provided";
        Assert.assertFalse(createOrderRequest.getSuccess());
        Assert.assertEquals(expected,createOrderRequest.getMessage());
    }

    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void CreateOrderWithIncorrectIngredientsTest() {
        User user = new User(EMAIL, PASSWORD, NAME);
        UserApi userApi = new UserApi();
        OrderApi orderApi = new OrderApi();

        UserResponse createUserResponse = userApi.createUser(user, 200);
        String token = createUserResponse.getAccessToken();
        String[] order = new String[] {null, null};
        OrderModel createOrder = new OrderModel(order);

        OrderModel createOrderRequest = orderApi.createOrder(token, createOrder, 400);

        String expected = "One or more ids provided are incorrect";
        Assert.assertFalse(createOrderRequest.getSuccess());
        Assert.assertEquals(expected,createOrderRequest.getMessage());
    }
}

