package com.example.recipesearch.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemInfoResponse {
    /**
     * This is the response from the api, when we ask for more detailed information,
     * some fields have not been serialized as they were not needed to demonstrate the concepts for this project
     */

    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("image")
    String image;

    @SerializedName("spoonacularSourceUrl")
    String spoonacularSourceUrl;

    @SerializedName("sourceUrl")
    String sourceUrl;

    @SerializedName("creditsText")
    String creditsText;

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImage() {
        return this.image;
    }

    public String getSpoonacularSourceUrl() {
        return this.spoonacularSourceUrl;
    }

    public String getCreditsText() {
        return this.creditsText;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }
}
