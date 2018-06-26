package com.example.francoislf.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.francoislf.mynews.Models.ArticleItem;
import com.example.francoislf.mynews.Models.RecyclerSectionFormat;
import com.example.francoislf.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.id_title_recyclerView) TextView mTextViewTitle;
    @BindView(R.id.id_date_recyclerView) TextView mTextViewDate;
    @BindView(R.id.id_section_recyclerView) TextView mTextViewSection;
    @BindView(R.id.id_image_recyclerView) ImageView mImageView;

    public static final String sImageWebMissing = "sImageWebMissing";

    RecyclerSectionFormat mRecyclerSectionFormat;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticleItem(ArticleItem articleItem, RequestManager glide){
        mRecyclerSectionFormat = new RecyclerSectionFormat(articleItem.getSection(), articleItem.getSubSection(),
                articleItem.getPubDate(), articleItem.getTitle(), articleItem.getPhotoUrl(), articleItem.getWebUrl());

        this.mTextViewDate.setText(mRecyclerSectionFormat.getRecyclerDate());
        this.mTextViewTitle.setText(mRecyclerSectionFormat.getBody());
        this.mTextViewSection.setText(mRecyclerSectionFormat.getSectionFormat());

        if (!mRecyclerSectionFormat.getWebImage().equals(sImageWebMissing))
            glide.load(mRecyclerSectionFormat.getWebImage()).apply(RequestOptions.centerInsideTransform()).into(mImageView);
        else mImageView.setImageResource(R.drawable.no_image);
    }
}
