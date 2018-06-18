package com.example.francoislf.mynews.Controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.example.francoislf.mynews.Models.ArticleItem;
import com.example.francoislf.mynews.Models.HttpRequest.ArticlesStreams;
import com.example.francoislf.mynews.Models.HttpRequest.MostPopular;
import com.example.francoislf.mynews.Models.HttpRequest.TopStories;
import com.example.francoislf.mynews.R;
import com.example.francoislf.mynews.Views.ArticleItemAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment{

    @BindView(R.id.fragment_root) LinearLayout mLinearLayout;
    @BindView(R.id.fragment_recyclerview) RecyclerView mRecyclerView;

    public static final String KEY_TITLE = "KEY_TITLE";
    private Disposable mDisposable;
    private List<ArticleItem> mArticleItemList;
    private ArticleItem mArticleItem;
    private ArticleItemAdapter mAdapter;

    public PageFragment() {}

    // Methods to create new instance of fragment
    public static PageFragment newInstance(String title){
        PageFragment fragment = new PageFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);

        configureRecyclerView();
        getArticles(getArguments().getString(KEY_TITLE,""));
        mLinearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        return view;
    }

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        this.mArticleItemList = new ArrayList<>();
        this.mAdapter = new ArticleItemAdapter(this.mArticleItemList, Glide.with(this));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // switch in order to distribute the good http request
    public void getArticles(String indexArticles){

        switch (indexArticles){
            case "Top Stories":
                executeHttpRequestTopStories("home");
                break;
            case "Most Popular" :
                executeHttpRequestMostPopular();
                break;
                default:
                    executeHttpRequestTopStories(indexArticles.toLowerCase());
                    break;
        }
    }

    /**
     *  MEMORY lEAKS PROTECTION
     */

    // Dispose subscription
    private void disposeWhenDestroy(){if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.disposeWhenDestroy();
    }

    /**
     *  HTTP (RxJAVA)
     */

    // Execute stream topStories
    private void executeHttpRequestTopStories(String section){
        this.mDisposable = ArticlesStreams.streamTopStories(section)
                .subscribeWith(new DisposableObserver<TopStories>() {
                    @Override
                    public void onNext(TopStories topStories) {
                        getUpdateUITopStories(topStories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGO", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAGO", "TopStories streams on complete");
                    }
                });
    }

    // Update UI with mArticleItem calibration about TopStories section
    private void getUpdateUITopStories(TopStories topStories){
        List<TopStories.Result> results = topStories.getResults();

        for (int i = 0 ; i < results.size() ; i++) {
            mArticleItem = new ArticleItem();
            mArticleItem.setSection(results.get(i).getSection());
            mArticleItem.setSubSection(results.get(i).getSubsection());
            mArticleItem.setPubDate(results.get(i).getPublishedDate());
            mArticleItem.setTitle(results.get(i).getTitle());
        if (!results.get(i).getMultimedia().isEmpty())   mArticleItem.setPhotoUrl("" + results.get(i).getMultimedia().get(0).getUrl());
        else mArticleItem.setPhotoUrl("NADA");
            mArticleItem.setWebUrl(results.get(i).getUrl());

            mArticleItemList.add(mArticleItem);
        }
        mAdapter.notifyDataSetChanged();
    }

    // Execute stream mostPopular
    private void executeHttpRequestMostPopular(){
        this.mDisposable = ArticlesStreams.streamMostPopular()
                .subscribeWith(new DisposableObserver<MostPopular>() {
                    @Override
                    public void onNext(MostPopular mostPopular) {
                        getUpdateUIMostPopular(mostPopular);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGO", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAGO", "Most popular streams on complete");
                    }
                });
    }

    // Update UI with mArticleItem calibration about MostPopular section
    private void getUpdateUIMostPopular(MostPopular mostPopular){
        List<MostPopular.Result> results = mostPopular.getResults();

        for (int i = 0 ; i < results.size() ; i++) {
            mArticleItem = new ArticleItem();
            mArticleItem.setSection(results.get(i).getSection());
            mArticleItem.setSubSection(results.get(i).getType());
            mArticleItem.setPubDate(results.get(i).getPublishedDate());
            mArticleItem.setTitle(results.get(i).getTitle());
            if (!results.get(i).getMedia().get(0).getMediaMetadata().isEmpty())
                mArticleItem.setPhotoUrl("" + results.get(i).getMedia().get(0).getMediaMetadata().get(0).getUrl());
            else mArticleItem.setPhotoUrl("NADA");
            mArticleItem.setWebUrl(results.get(i).getUrl());

            mArticleItemList.add(mArticleItem);
        }
        mAdapter.notifyDataSetChanged();
    }
}
