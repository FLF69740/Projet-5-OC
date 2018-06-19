package com.example.francoislf.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.francoislf.mynews.Models.ArticleItem;
import com.example.francoislf.mynews.R;
import java.util.List;

public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<ArticleItem> mArticleItems;

    private RequestManager mGlide;

    public ArticleItemAdapter(List<ArticleItem> articleItems, RequestManager glide){
        this.mArticleItems = articleItems;
        this.mGlide = glide;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_item_recyclerview, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.updateWithArticleItem(this.mArticleItems.get(position), this.mGlide);
    }

    @Override
    public int getItemCount() {
        return this.mArticleItems.size();
    }

    // Define the article from the list with the position
    public ArticleItem getArticle(int position){
        return this.mArticleItems.get(position);
    }
}
