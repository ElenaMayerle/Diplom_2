import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest extends BaseTest{

    ValidatableResponse response;

    @Test
    @DisplayName("Успешная авторизация")
    public void loginSuccess(){
        response = steps.userLogin();
        response.assertThat().statusCode(200).and().body("success",equalTo(true));
    }
    @Test
    @DisplayName("Попытка авторизации с неправильным email")
    public void loginWithWrongEmailShowsError(){
        steps.user.setEmail("qwerty");
        response = steps.userLogin();
        response.assertThat().statusCode(401).and().body("message",equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Попытка авторизации с неправильным паролем")
    public void loginWithWrongPasswordShowsError(){
        steps.user.setPassword("123");
        response = steps.userLogin();
        response.assertThat().statusCode(401).and().body("message",equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Попытка авторизации с неправильным email и паролем")
    public void loginWithWrongCredentialsShowsError(){
        steps.user.setEmail("qwerty");
        steps.user.setPassword("123");
        response = steps.userLogin();
        response.assertThat().statusCode(401).and().body("message",equalTo("email or password are incorrect"));
    }

}

