package com.example.recipesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.recipesearch.adapter.RecipeListAdapter;
import com.example.recipesearch.network.QueryRecipes;
import com.example.recipesearch.network.QueryResponse;
import com.example.recipesearch.network.RetroRecipe;
import com.example.recipesearch.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecipeListAdapter adapter;
    private RecyclerView recyclerView;
    private Menu menu;
    QueryRecipes service = RetrofitClient.getRetrofitClient().create(QueryRecipes.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryAndGenerateList("burger");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_options, menu);
        // Get the search view and then add query text listeners
        SearchView sv = (SearchView) menu.findItem(R.id.search_recipes).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryAndGenerateList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryAndGenerateList(newText);
                return false;
            }
        });
        return true;
    }

    /**
     * This takes in a string for query and calls the api, then generates the data list, which is then rendered by the recycler view
     * @param query: any string to search recipes for
     */
    public void queryAndGenerateList(String query) {
        /*Create handle for the RetrofitInstance interface*/
        Call<QueryResponse> call = service.queryRecipes(query, 10, QueryRecipes.apiKey, true);
        call.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                generateDataList(response.body().getRecipeList(), response.body().getBaseURI());
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This generates the recycler view which has a preview of a list of recipes
     * @param photoList: query response's results, which are used to render individual items
     * @param baseURI: URI for the recipe
     */
    private void generateDataList(final List<RetroRecipe> photoList, String baseURI) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new RecipeListAdapter(this, photoList, baseURI, new RecipeListAdapter.RecipeItemOnClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                //TODO: Details activity for this
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", photoList.get(position).getId());
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
