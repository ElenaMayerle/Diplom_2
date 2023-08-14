import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest extends BaseTest{
    ValidatableResponse response;
    @Before
    public void setUp(){
        steps.getIngredients();
        steps.doList();
        steps.createOrderwWithToken();
    }
    @Test
    @DisplayName("Получение списка заказов авторизованным пользователем")
    public void getOrderWithAuthSuccess(){
        response = steps.getOrdersWithToken();
        response.assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("orders.number",notNullValue());
    }
    @Test
    @DisplayName("Попытка получения списка заказов неавторизованным пользователем")
    public void getOrdersWithoutAuthShowsError(){
        response = steps.getOrders();
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }

}
