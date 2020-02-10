package com.example.recipesearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipesearch.R;
import com.example.recipesearch.network.RetroRecipe;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.CustomViewHolder> {

    // Custom adapter to make a list of items on the main activity

    private List<RetroRecipe> dataList;
    private Context context;
    private String baseUri;
    private RecipeItemOnClickListener onClickListener;

    // Constructor for adapter
    public RecipeListAdapter(Context context, List<RetroRecipe> dataList, String baseUri, RecipeItemOnClickListener onClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.baseUri = baseUri;
        this.onClickListener = onClickListener;
    }

    // View holder that creates a view for a single item
    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;

        public TextView txtTitle;
        public ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
        }

        @Override
        public void onClick(View v) {
        }
    }

    // Interface for onclick listener for individual recipe item
    public interface RecipeItemOnClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Adds a onclick listener to a view corresponding to an individual item
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        final RecyclerView.ViewHolder viewHolder = new CustomViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onItemClick((int)v.getTag(), v);
            }
        });
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // ViewHolder sets the text to recipe based on it's position in the datalist,
        // this position corresponds to it's actual position in the results returned from the query
        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.mView.setTag(position);

        // ViewHolder uses picasso to render the image, downloading it, if it is not available locally,
        // or using the same image from picasso cache
        if(holder.coverImage.getDrawable() == null) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(baseUri + dataList.get(position).getImage())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.coverImage);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
