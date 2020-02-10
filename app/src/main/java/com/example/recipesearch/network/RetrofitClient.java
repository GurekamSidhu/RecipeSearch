package com.example.recipesearch.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.spoonacular.com";//"/recipes/search?apiKey=4dd22cfe4b4b437dbf3f2d295d5c3002&number=5&query=";

    // Provisions a retrofit client to make calls to the base url
    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
