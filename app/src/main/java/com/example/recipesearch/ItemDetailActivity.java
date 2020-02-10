package com.example.recipesearch;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesearch.network.ItemInfoResponse;
import com.example.recipesearch.network.QueryRecipes;
import com.example.recipesearch.network.RetrofitClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailActivity extends AppCompatActivity {

    QueryRecipes service = RetrofitClient.getRetrofitClient().create(QueryRecipes.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Enables the back button, which takes us to home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Used to get parameters passed through the intent, here we basically get the id of the item we want to render details of
        Bundle b = getIntent().getExtras();
        int recipeID = -1; // or other values
        if(b != null)
            recipeID = b.getInt("id");

        /*
        call to API (/recipes/{id}/information) to get detailed recipe information
         */
        Call<ItemInfoResponse> call = service.getItemInfo(recipeID, QueryRecipes.apiKey, false);
        call.enqueue(new Callback<ItemInfoResponse>() {
            @Override
            public void onResponse(Call<ItemInfoResponse> call, Response<ItemInfoResponse> response) {
                generateItemDetail(response.body());
            }

            @Override
            public void onFailure(Call<ItemInfoResponse> call, Throwable t) {
                Toast.makeText(ItemDetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Generates a detailed view of the item by extracting information from the item info response
     */
    private void generateItemDetail(ItemInfoResponse itemInfoResponse) {
        TextView textView = findViewById(R.id.item_detail_title);
        textView.setText(itemInfoResponse.getTitle());

        ImageView imageView = findViewById(R.id.item_detail_image);

        // Image is downloaded only if it was not cached, if it was cached, then is not downloaded
        if(imageView.getDrawable() == null) {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.downloader(new OkHttp3Downloader(this));
            builder.build().load(itemInfoResponse.getImage())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }

        // Webview client is reset, to prevent the page from opening in a browser
        WebView webView = findViewById(R.id.spoonacular_web_page);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(itemInfoResponse.getSourceUrl());
    }

    // Opens main activity using a new intent
    public void openActivity() {
        Intent intent = new Intent(ItemDetailActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // When user clicks home, we open the main activity again
                openActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
