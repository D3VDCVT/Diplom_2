import api.OrderApi;
import api.UserApi;
import io.qameta.allure.junit4.DisplayName;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import static models.Constants.*;

public class GetOrderTest extends BaseTest{

    @DisplayName("Получение заказа")
    @Test
    public void getOrderTest() {
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
        orderApi.createOrder(token, createOrder, 200);

        GetOrderResponse getOrderResponse = orderApi.getOrder(token, 200);

        Assert.assertTrue(getOrderResponse.getSuccess());
        Assert.assertNotNull(getOrderResponse.getOrders().get(0).get_id());
        Assert.assertEquals(firstIngredient, getOrderResponse.getOrders().get(0).getIngredients().get(0));
        Assert.assertEquals(secondIngredient, getOrderResponse.getOrders().get(0).getIngredients().get(1));
        Assert.assertNotNull(getOrderResponse.getOrders().get(0).getStatus());
        Assert.assertNotNull(getOrderResponse.getOrders().get(0).getNumber());
        Assert.assertNotNull(getOrderResponse.getOrders().get(0).getCreatedAt());
        Assert.assertNotNull(getOrderResponse.getOrders().get(0).getUpdatedAt());
        Assert.assertNotNull(getOrderResponse.getTotal());
        Assert.assertNotNull(getOrderResponse.getTotalToday());
    }

    @DisplayName("Получение заказа без авторизации")
    @Test
    public void getOrderWithoutAuthorizationTest() {
        OrderApi orderApi = new OrderApi();
        GetOrderResponse getOrderResponse = orderApi.getOrderWithOutToken(401);

        String expected = "You should be authorised";
        Assert.assertFalse(getOrderResponse.getSuccess());
        Assert.assertEquals(expected, getOrderResponse.getMessage());
    }
}
