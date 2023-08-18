import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest extends BaseTest{
    ValidatableResponse response;
    @Before
    public void setUp(){
        steps.getIngredients();
    }
    @Test
    @DisplayName("Создание заказа с ингредиентами неавторизованным пользователем")
    public void createOrderWithoutAuthWithIngredientsSuccess(){
        steps.doList();
        response = steps.createOrder();
        response.assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("name",notNullValue());
    }
    @Test
    @DisplayName("Попытка создания заказа без ингредиентов")
    public void createOrderWithoutIngredientsShowsError(){
        List<String> list = new ArrayList<>();
        steps.setList(list);
        response = steps.createOrder();
        response.assertThat().statusCode(400).and().body("message",equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Попытка создания заказа с неправильным хэшем ингредиентов")
    public void createOrderWithWrongHashShowsError(){
        List<String> list = new ArrayList<>();
        list.add(steps.getDataIngredients().getData().get(0).get_id()+1);
        list.add(steps.getDataIngredients().getData().get(2).get_id()+1);
        steps.setList(list);
        response = steps.createOrder();
        response.assertThat().statusCode(500);
    }
    @Test
    @DisplayName("Создание заказа с ингредиентами авторизованным пользователем")
    public void createOrderWithAuthWithIngredientsSuccess(){
        steps.doList();
        response = steps.createOrderwWithToken();
        response.assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("name",notNullValue());
    }

}
