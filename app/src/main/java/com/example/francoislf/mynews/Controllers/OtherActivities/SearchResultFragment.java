package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.francoislf.mynews.Models.ArticleItem;
import com.example.francoislf.mynews.Models.DateFormatTransformer;
import com.example.francoislf.mynews.Models.HttpRequest.ArticleSearch;
import com.example.francoislf.mynews.Models.HttpRequest.ArticlesStreams;
import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.example.francoislf.mynews.Views.ArticleItemAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private SearchPreferences mSearchPreferences;
    private Disposable mDisposable;
    private List<ArticleItem> mArticleItemList;
    private ArticleItem mArticleItem;
    private ArticleItemAdapter mAdapter;

    @BindView(R.id.fragment_recyclerview_search) RecyclerView mRecyclerView;


    public SearchResultFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        ButterKnife.bind(this, view);

        configureRecyclerView();

        return view;
    }

    // implement datas from searchPreferences to Http Request process
    public void updateFragmentData(SearchPreferences searchPreferences){
        this.mSearchPreferences = searchPreferences;

        this.executeHttpRequest(mSearchPreferences.getSearchString(),
                new DateFormatTransformer(mSearchPreferences.getBeginDateString()).getDateStringOutput(),
                new DateFormatTransformer(mSearchPreferences.getEndDateString()).getDateStringOutput());
    }

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        this.mArticleItemList = new ArrayList<>();
        this.mAdapter = new ArticleItemAdapter(this.mArticleItemList, Glide.with(this));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     *  HTTP (RxJAVA)
     */

    // Execute Search Articles Stream
    private void executeHttpRequest(String text, String start, String end){
        this.mDisposable = ArticlesStreams.streamArticleSearch(text, start, end)
                .map(getFunctionNewDeskFilter())
                .subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch articleSearch) {
                        getUpdateUI(articleSearch);
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

    // Create function to add new_desk filter
    private Function<ArticleSearch, ArticleSearch> getFunctionNewDeskFilter(){
        return new Function<ArticleSearch, ArticleSearch>() {
            @Override
            public ArticleSearch apply(ArticleSearch articleSearch) throws Exception {
                ArrayList<ArticleSearch.Doc> filteredDoc = new ArrayList<>();
                for (int j = 0; j < mSearchPreferences.getListCheckBoxString().size(); j++) {
                    for (int i = 0; i < articleSearch.getResponse().getDocs().size(); i++) {
                        if (articleSearch.getResponse().getDocs().get(i).getNewDesk().equals(mSearchPreferences.getListCheckBoxString().get(j)))
                            filteredDoc.add(articleSearch.getResponse().getDocs().get(i));
                    }
                }
                articleSearch.getResponse().setDocs(filteredDoc);
                return articleSearch;
            }
        };
    }

    // Update UI
    private void getUpdateUI(ArticleSearch articleSearch){
        List<ArticleSearch.Doc> results = articleSearch.getResponse().getDocs();
        if (!results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                mArticleItem = new ArticleItem();
                mArticleItem.setSection(mSearchPreferences.getSearchString());
                mArticleItem.setSubSection(results.get(i).getNewDesk());
                mArticleItem.setPubDate(results.get(i).getPubDate());
                mArticleItem.setTitle(results.get(i).getSnippet());
                if (!results.get(i).getMultimedia().isEmpty())
                    mArticleItem.setPhotoUrl("https://static01.nyt.com/" + results.get(i).getMultimedia().get(0).getUrl());
                else mArticleItem.setPhotoUrl("NADA");
                mArticleItem.setWebUrl(results.get(i).getWebUrl());

                mArticleItemList.add(mArticleItem);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     *  MEMORY lEAKS PROTECTION
     */

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.disposeWhenDestroy();
    }

}