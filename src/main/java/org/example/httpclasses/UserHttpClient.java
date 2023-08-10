package org.example.httpclasses;

import io.restassured.response.ValidatableResponse;
import org.example.Const;
import org.example.pojo.User;

public class UserHttpClient extends BaseHttp {
    private final String url;
    public UserHttpClient(String baseUrl){
        super();
        this.url = baseUrl + Const.CREATE_USER_ENDPOINT;
    }
    public ValidatableResponse createUser(User user){
        return doPostRequest(url,user);
    }

}
