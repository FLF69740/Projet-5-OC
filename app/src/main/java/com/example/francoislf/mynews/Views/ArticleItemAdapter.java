package com.example.francoislf.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.francoislf.mynews.Models.ArticleItem;
import com.example.francoislf.mynews.R;
import java.util.List;

public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<ArticleItem> mArticleItems;

    public ArticleItemAdapter(List<ArticleItem> articleItems){
        this.mArticleItems = articleItems;
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
        holder.updateWithArticleItem(this.mArticleItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mArticleItems.size();
    }
}
