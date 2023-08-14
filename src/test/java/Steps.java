import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.Const;
import org.example.httpclasses.*;
import org.example.pojo.DataIngredients;
import org.example.pojo.Order;
import org.example.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class Steps {
    DataIngredients dataIngredients;
    List<String> list;
    String token;
    Faker faker = new Faker(Locale.forLanguageTag("ru"));
    User user = new User(faker.elderScrolls().creature().replaceAll(" ", "") + "_" + faker.animal().name().replaceAll(" ", "") + "@yandex.ru", faker.name().firstName(), "qwerty");
    UserHttpClient userHttpClient = new UserHttpClient(Const.URL);
    UserInfoHttpClient userInfoHttpClient = new UserInfoHttpClient(Const.URL);
    UserLoginHttpClient userLoginHttpClient = new UserLoginHttpClient(Const.URL);
    OrderHttpClient orderHttpClient = new OrderHttpClient(Const.URL);
    IngredientsHttpClient ingredientsHttpClient = new IngredientsHttpClient(Const.URL);
    @Step("Получение набора ингредиентов")
    public void getIngredients(){
        dataIngredients=ingredientsHttpClient.getIngredients().extract().as(DataIngredients.class);
    }
    @Step("Создание списка ингредиентов")
    public void doList(){
        list = new ArrayList<>();
        list.add(dataIngredients.getData().get(0).get_id());
        list.add(dataIngredients.getData().get(2).get_id());
        list.add(dataIngredients.getData().get(4).get_id());
    }
    @Step("Создание заказа авторизованным пользователем")
    public ValidatableResponse createOrderwWithToken(){
        Order order = new Order(list);
        return orderHttpClient.createOrder(order,token);
    }
    @Step("Создание заказа неавторизованным пользователем")
    public ValidatableResponse createOrder(){
        Order order = new Order(list);
        return orderHttpClient.createOrder(order);
    }
    @Step("Создание пользователя")
    public ValidatableResponse createUser(){
        return userHttpClient.createUser(user);
    }

    @Step("Создание пользователя и получение токена авторизации")
    public void createUserAndGetToken(){
        token = userHttpClient.createUser(user).extract().body().path("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser(){
        userInfoHttpClient.deleteUser(token);
    }
    @Step("Получение списка заказов авторизованного пользователя")
    public ValidatableResponse getOrdersWithToken(){
        return orderHttpClient.getOrders(token);
    }
    @Step("Получение списка заказов неавторизованного пользователя")
    public ValidatableResponse getOrders(){
        return orderHttpClient.getOrders();
    }
    @Step("Авторизация пользователя")
    public ValidatableResponse userLogin(){
        return userLoginHttpClient.userLogin(user);
    }
    @Step("Изменение данных авторизованного пользователя")
    public ValidatableResponse changeInfoWithToken(){
        return userInfoHttpClient.changeInfoWithAuth(token,user);
    }
    @Step("Изменение данных неавторизованного пользователя")
    public ValidatableResponse changeInfo(){
        return userInfoHttpClient.changeInfoWithoutAuth(user);
    }
}
