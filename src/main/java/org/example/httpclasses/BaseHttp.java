package org.example.httpclasses;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

abstract class BaseHttp {
    public ValidatableResponse doPostRequest(String url, Object body){
        return given(baseRequest()).body(body).post(url).then();
    }
    public ValidatableResponse doPostRequest(String url,Object body,String token){
        return given(baseRequest()).header("Authorization", token).body(body).post(url).then();
    }
    public ValidatableResponse doGetRequest(String url){
       return given(baseRequest()).get(url).then();
    }
    public ValidatableResponse doGetRequest(String url, String token){
        return given(baseRequest()).header("Authorization", token).get(url).then();
    }
    public ValidatableResponse doDeleteRequest(String url, String token){
        return given(baseRequest()).header("Authorization", token).delete(url).then();
    }
    public ValidatableResponse doPatchRequest(String url, String token, Object body){
        return given(baseRequest()).header("Authorization", token).body(body).patch(url).then();
    }
    public ValidatableResponse doPatchRequest(String url, Object body){
        return given(baseRequest()).body(body).patch(url).then();
    }

    private RequestSpecification baseRequest(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setRelaxedHTTPSValidation()
                .build();
    }
}


