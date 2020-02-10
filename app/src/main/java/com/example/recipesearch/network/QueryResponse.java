package com.example.recipesearch.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryResponse {
    /**
     * Response from spoonacular server when searching for recipes with keywords
     */

    @SerializedName("offset")
    int offset;

    @SerializedName("number")
    int number;

    @SerializedName("results")
    List<RetroRecipe> recipeList;

    @SerializedName("totalResults")
    int totalResults;

    @SerializedName("baseUri")
    String baseUri;


    public List<RetroRecipe> getRecipeList() {
        return recipeList;
    }

    // Object for a detailed query reponse, used in retrofit calls
    public QueryResponse (
            int offset, int number, List<RetroRecipe>recipeList,
            int totalResults, String baseUri)

    {
        this.number = number;
        this.offset = offset;
        this.recipeList = recipeList;
        this.totalResults = totalResults;
        this.baseUri = baseUri;
    }

    public String getBaseURI() {
        return baseUri;
    }
}
