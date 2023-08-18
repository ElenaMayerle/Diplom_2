import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    ValidatableResponse response;
    Steps steps = new Steps();

    @Test
    @DisplayName("Успешное создание пользователя")
    public void createUserSuccess(){
        response = steps.createUser();
        steps.setToken(response.extract().body().path("accessToken"));
        response.assertThat().statusCode(200).and().body("success",equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания двух одинаковых пользователей")
    public void createTwoIdenticalUsersShowsError(){
        steps.createUserAndGetToken();
        response = steps.createUser();
        response.assertThat().statusCode(403).and().body("message",equalTo("User already exists"));
    }
    @Test
    @DisplayName("Попытка создать пользователя без email")
    public void createUserWithoutEmailShowsError(){
        steps.user.setEmail(null);
        response = steps.createUser();
        response.assertThat().statusCode(403).and().body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Попытка создать пользователя без имени")
    public void createUserWithoutNameShowsError(){
        steps.user.setName(null);
        response = steps.createUser();
        response.assertThat().statusCode(403).and().body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Попытка создать пользователя без пароля")
    public void createUserWithoutPasswordShowsError(){
        steps.user.setPassword(null);
        response = steps.createUser();
        response.assertThat().statusCode(403).and().body("message",equalTo("Email, password and name are required fields"));
    }
    @After
    public void tearDown(){
        try {
            steps.deleteUser();
        } catch (Exception exception){
            Exception e;
        }
    }

}
