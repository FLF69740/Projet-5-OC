package com.example.francoislf.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.francoislf.mynews.Models.ArticleItem;
import com.example.francoislf.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.id_title_recyclerView) TextView mTextView;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticleItem(ArticleItem articleItem){
        this.mTextView.setText(articleItem.getTitle());
    }
}
