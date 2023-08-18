import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserInfoTest extends BaseTest{
    ValidatableResponse response;

    @Test
    @DisplayName("Изменение email авторизованным пользователем")
    public void changeEmailWithAuthSuccess(){
        steps.user.setEmail(steps.user.getEmail()+".ru");
        response = steps.changeInfoWithToken();
        response.assertThat().statusCode(200).and().body("user.email",equalTo(steps.user.getEmail().toLowerCase()));
    }
    @Test
    @DisplayName("Изменение имени авторизованным пользователем")
    public void changeNameWithAuthSuccess() {
        steps.user.setName(steps.user.getName() + "12345");
        response = steps.changeInfoWithToken();
        response.assertThat().statusCode(200).and().body("user.name", equalTo(steps.user.getName()));
    }
    @Test
    @DisplayName("Изменение email и имени авторизованным пользователем")
    public void changeEmailAndNameWithAuthSuccess() {
        steps.user.setEmail(steps.user.getEmail()+".ru");
        steps.user.setName(steps.user.getName() + "12345");
        response = steps.changeInfoWithToken();
        response.assertThat().statusCode(200).and().body("user.name", equalTo(steps.user.getName())).and().body("user.email",equalTo(steps.user.getEmail().toLowerCase()));
    }
    @Test
    @DisplayName("Попытка изменить email неавторизованным пользователем")
    public void changeEmailWithoutAuthShowsError(){
        steps.user.setEmail(steps.user.getEmail()+".ru");
        response = steps.changeInfo();
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Попытка изменить имя неавторизованным пользователем")
    public void changeNameWithoutAuthShowsError() {
        steps.user.setName(steps.user.getName() + "12345");
        response = steps.changeInfo();
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Попытка изменить email и имя неавторизованным пользователем")
    public void changeEmailAndNameWithoutAuthShowsError() {
        steps.user.setEmail(steps.user.getEmail()+".ru");
        steps.user.setName(steps.user.getName() + "12345");
        response = steps.changeInfo();
        response.assertThat().statusCode(401).and().body("message",equalTo("You should be authorised"));
    }

    }
