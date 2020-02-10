package com.example.recipesearch.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetroRecipe {
    /**
     * Object representing different properties of a recipe
     */

    @SerializedName("id")
    private Integer id;

    @SerializedName("image")
    private String image;

    @SerializedName("imageUrls")
    private List<String> imageUrls;

    @SerializedName("readyInMinutes")
    private Integer readyInMinutes;

    @SerializedName("servings")
    private Integer servings;

    @SerializedName("title")
    String title;

    @Override
    public String toString() {
        return "RetroRecipe{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", usedIngredientCount=" + imageUrls +
                ", readyInMinutes=" + readyInMinutes +
                ", likes=" + servings +
                '}';
    }

    public int getId() { return id; }

    public String getImage() { return image; }

    public List<String> getImageUrls() { return imageUrls; }


    public String getTitle() { return title; }

    public int getServings() { return servings; }

    //The constructor for a recipe object
    public RetroRecipe (int id, String title, String image, List<String> imageUrls, int readyInMinutes, int servings) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageUrls = imageUrls;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
    }
}
