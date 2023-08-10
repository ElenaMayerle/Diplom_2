import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.example.httpclasses.UserHttpClient;
import org.example.httpclasses.UserInfoHttpClient;
import org.example.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserInfoTest {
    Faker faker = new Faker(Locale.forLanguageTag("ru"));
    User user = new User(faker.elderScrolls().creature().replaceAll(" ", "") + "_" + faker.animal().name().replaceAll(" ", "") + "@yandex.ru", faker.name().firstName(), "Qwerty");
    String token;
    UserHttpClient userHttpClient = new UserHttpClient(Const.URL);
    UserInfoHttpClient userInfoHttpClient = new UserInfoHttpClient(Const.URL);
    ValidatableResponse response;
    @Before
    public void setUp() {
        token = userHttpClient.createUser(user).extract().body().path("accessToken");
    }
    @Test
    @DisplayName("Изменение email авторизованным пользователем")
    public void changeEmailWithAuthSuccess(){
        user.setEmail(user.getEmail()+".ru");
        response = userInfoHttpClient.changeInfoWithAuth(token,user);
        response.assertThat().statusCode(200).and().body("user.email",equalTo(user.getEmail().toLowerCase()));
    }
    @Test
    @DisplayName("Изменение имени авторизованным пользователем")
    public void changeNameWithAuthSuccess() {
        user.setName(user.getName() + "12345");
        response = userInfoHttpClient.changeInfoWithAuth(token, user);
        response.assertThat().statusCode(200).and().body("user.name", equalTo(user.getName()));
    }
    @Test
    @DisplayName("Изменение email и имени авторизованным пользователем")
    public void changeEmailAndNameWithAuthSuccess() {
        user.setEmail(user.getEmail()+".ru");
        user.setName(user.getName() + "12345");
        response = userInfoHttpClient.changeInfoWithAuth(token, user);
        response.assertThat().statusCode(200).and().body("user.name", equalTo(user.getName())).and().body("user.email",equalTo(user.getEmail().toLowerCase()));
    }
    @Test
    @DisplayName("Попытка изменить email неавторизованным пользователем")
    public void changeEmailWithoutAuthShowsError(){
        user.setEmail(user.getEmail()+".ru");
        response = userInfoHttpClient.changeInfoWithoutAuth(user);
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Попытка изменить имя неавторизованным пользователем")
    public void changeNameWithoutAuthShowsError() {
        user.setName(user.getName() + "12345");
        response = userInfoHttpClient.changeInfoWithoutAuth(user);
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Попытка изменить email и имя неавторизованным пользователем")
    public void changeEmailAndNameWithoutAuthShowsError() {
        user.setEmail(user.getEmail()+".ru");
        user.setName(user.getName() + "12345");
        response = userInfoHttpClient.changeInfoWithoutAuth(user);
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }
    @After
    public void tearDown(){
        userInfoHttpClient.deleteUser(token);
    }

    }
