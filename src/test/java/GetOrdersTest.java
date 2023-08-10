import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.example.httpclasses.IngredientsHttpClient;
import org.example.httpclasses.OrderHttpClient;
import org.example.httpclasses.UserHttpClient;
import org.example.httpclasses.UserInfoHttpClient;
import org.example.pojo.DataIngredients;
import org.example.pojo.Order;
import org.example.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {
    Faker faker = new Faker(Locale.forLanguageTag("ru"));
    User user = new User(faker.elderScrolls().creature().replaceAll(" ", "") + "_" + faker.animal().name().replaceAll(" ", "") + "@yandex.ru", faker.name().firstName(), "Qwerty");
    String token;
    UserHttpClient userHttpClient = new UserHttpClient(Const.URL);
    UserInfoHttpClient userInfoHttpClient = new UserInfoHttpClient(Const.URL);
    IngredientsHttpClient ingredientsHttpClient = new IngredientsHttpClient(Const.URL);
    OrderHttpClient orderHttpClient = new OrderHttpClient(Const.URL);
    DataIngredients dataIngredients;
    ValidatableResponse response;
    Order order;

    @Before
    public void setUp(){
        token = userHttpClient.createUser(user).extract().body().path("accessToken");
        dataIngredients = ingredientsHttpClient.getIngredients().extract().as(DataIngredients.class);
        List<String> list = new ArrayList<>();
        list.add(dataIngredients.getData().get(0).get_id());
        list.add(dataIngredients.getData().get(2).get_id());
        list.add(dataIngredients.getData().get(4).get_id());
        order = new Order(list);
        orderHttpClient.createOrder(order,token);

    }
    @Test
    @DisplayName("Получение списка заказов авторизованным пользователем")
    public void getOrderWithAuthSuccess(){
        response=orderHttpClient.getOrders(token);
        response.assertThat().statusCode(200).and().body("success",equalTo(true)).and().body("orders.number",notNullValue());
    }
    @Test
    @DisplayName("Попытка получения списка заказов неавторизованным пользователем")
    public void getOrdersWithoutAuthShowsError(){
        response=orderHttpClient.getOrders();
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }

    @After
    public void tearDown(){
        userInfoHttpClient.deleteUser(token);
    }
}
