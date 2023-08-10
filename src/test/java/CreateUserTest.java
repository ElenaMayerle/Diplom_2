import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Const;
import org.example.pojo.User;
import org.example.httpclasses.UserInfoHttpClient;
import org.example.httpclasses.UserHttpClient;
import org.junit.After;
import org.junit.Test;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreateUserTest {
    Faker faker = new Faker(Locale.forLanguageTag("ru"));
    User user = new User(faker.elderScrolls().creature().replaceAll(" ", "")+"_"+faker.animal().name().replaceAll(" ", "")+"@yandex.ru", faker.name().firstName(), "qwerty");;
    String token;
    ValidatableResponse response;
    private final UserHttpClient userHttpClient = new UserHttpClient(Const.URL);
    private final UserInfoHttpClient userInfoHttpClient = new UserInfoHttpClient(Const.URL);

    @Test
    @DisplayName("Успешное создание пользователя")
    public void createUserSuccess(){
        response = userHttpClient.createUser(user);
        token = response.extract().body().path("accessToken");
        response.assertThat().statusCode(200).and().body("success",equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания двух одинаковых пользователей")
    public void createTwoIdenticalUsersShowsError(){
        userHttpClient.createUser(user);
        response = userHttpClient.createUser(user);
        response.assertThat().statusCode(403).and().body("message",equalTo("User already exists"));
    }
    @Test
    @DisplayName("Попытка создать пользователя без email")
    public void createUserWithoutEmailShowsError(){
        user.setEmail(null);
        response = userHttpClient.createUser(user);
        response.assertThat().statusCode(403).and().body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Попытка создать пользователя без имени")
    public void createUserWithoutNameShowsError(){
        user.setName(null);
        response = userHttpClient.createUser(user);
        response.assertThat().statusCode(403).and().body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Попытка создать пользователя без пароля")
    public void createUserWithoutPasswordShowsError(){
        user.setPassword(null);
        response = userHttpClient.createUser(user);
        response.assertThat().statusCode(403).and().body("message",equalTo("Email, password and name are required fields"));
    }
    @After
    public void tearDown(){
        try {
        userInfoHttpClient.deleteUser(token);
        } catch (Exception exception){
            Exception e;
        }

}

}
