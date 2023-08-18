package org.example.httpclasses;

import io.restassured.response.ValidatableResponse;
import org.example.Const;
import org.example.pojo.User;

public class UserInfoHttpClient extends BaseHttp{
    private final String url;
    public UserInfoHttpClient(String baseUrl){
        super();
        this.url = baseUrl + Const.USER_INFO_ENDPOINT;
    }

    public void deleteUser(String token){
        doDeleteRequest(url, token);
    }
    public ValidatableResponse changeInfoWithAuth(String token, User user){
        return doPatchRequest(url,token,user);
    }
    public ValidatableResponse changeInfoWithoutAuth(User user){
        return doPatchRequest(url,user);
    }
}
