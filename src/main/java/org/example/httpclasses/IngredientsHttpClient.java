package org.example.httpclasses;

import io.restassured.response.ValidatableResponse;
import org.example.Const;

public class IngredientsHttpClient extends BaseHttp{
    private final String url;
    public IngredientsHttpClient(String baseUrl){
        super();
        this.url = baseUrl + Const.INGREDIENTS_ENDPOINT;
    }
    public ValidatableResponse getIngredients(){
        return doGetRequest(url);
    }

}
