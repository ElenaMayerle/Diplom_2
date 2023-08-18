package org.example.httpclasses;

import io.restassured.response.ValidatableResponse;
import org.example.Const;
import org.example.pojo.Order;

public class OrderHttpClient extends BaseHttp{
    private final String url;
    public OrderHttpClient(String baseUrl){
        super();
        this.url = baseUrl + Const.ORDERS_ENDPOINT;
    }
    public ValidatableResponse createOrder(Order order){
        return doPostRequest(url,order);
    }
    public ValidatableResponse createOrder(Order order,String token){
        return doPostRequest(url,order,token);
    }
    public ValidatableResponse getOrders(String token){
        return doGetRequest(url,token);
    }
    public ValidatableResponse getOrders(){
        return doGetRequest(url);
    }

}
