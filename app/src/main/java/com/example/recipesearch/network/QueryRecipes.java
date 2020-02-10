package com.example.recipesearch.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QueryRecipes {
    /**
     * Interface for querying the recipes form the spoonacular database
     */
    String apiKey = "4dd22cfe4b4b437dbf3f2d295d5c3002";

    // Search all recipes based on a keyword
    @GET("/recipes/search")
    Call<QueryResponse> queryRecipes(
            @Query(value = "query") String query,
            @Query(value = "number") int number,
            @Query(value = "apiKey") String apiKey,
            @Query(value = "instructionsRequired") boolean instructionsRequired
    );

    // Get detailed information for a recipe by passing in it's ID
    @GET("/recipes/{id}/information")
    Call<ItemInfoResponse> getItemInfo(
            @Path(value = "id") int id,
            @Query(value = "apiKey") String apiKey,
            @Query(value = "includeNutrition") boolean includeNutrition
    );
}
