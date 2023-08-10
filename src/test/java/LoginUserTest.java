import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.example.httpclasses.UserHttpClient;
import org.example.httpclasses.UserInfoHttpClient;
import org.example.httpclasses.UserLoginHttpClient;
import org.example.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    Faker faker = new Faker(Locale.forLanguageTag("ru"));
    User user = new User(faker.elderScrolls().creature().replaceAll(" ", "") + "_" + faker.animal().name().replaceAll(" ", "") + "@yandex.ru", faker.name().firstName(), "qwerty");;
    String token;
    ValidatableResponse response;
    UserHttpClient userHttpClient = new UserHttpClient(Const.URL);
    UserLoginHttpClient userLoginHttpClient = new UserLoginHttpClient(Const.URL);
    UserInfoHttpClient userInfoHttpClient = new UserInfoHttpClient(Const.URL);
    @Before
    public void setUp() {
        token = userHttpClient.createUser(user).extract().body().path("accessToken");
    }
    @Test
    @DisplayName("Успешная авторизация")
    public void loginSuccess(){
        response = userLoginHttpClient.userLogin(user);
        response.assertThat().statusCode(200).and().body("success",equalTo(true));
    }
    @Test
    @DisplayName("Попытка авторизации с неправильным email")
    public void loginWithWrongEmailShowsError(){
        user.setEmail("qwerty");
        response = userLoginHttpClient.userLogin(user);
        response.assertThat().statusCode(401).and().body("message",equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Попытка авторизации с неправильным паролем")
    public void loginWithWrongPasswordShowsError(){
        user.setPassword("123");
        response = userLoginHttpClient.userLogin(user);
        response.assertThat().statusCode(401).and().body("message",equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Попытка авторизации с неправильным email и паролем")
    public void loginWithWrongCredentialsShowsError(){
        user.setEmail("qwerty");
        user.setPassword("123");
        response = userLoginHttpClient.userLogin(user);
        response.assertThat().statusCode(401).and().body("message",equalTo("email or password are incorrect"));
    }
    @After
    public void tearDown(){
        userInfoHttpClient.deleteUser(token);
    }
}

