package org.example.httpclasses;

import io.restassured.response.ValidatableResponse;
import org.example.Const;
import org.example.pojo.User;

public class UserLoginHttpClient extends BaseHttp{
    private final String url;

    public UserLoginHttpClient(String baseUrl){
        super();
        this.url = baseUrl + Const.LOGIN_USER_ENDPOINT;
    }
    public ValidatableResponse userLogin(User user){
        return doPostRequest(url,user);
    }

}
