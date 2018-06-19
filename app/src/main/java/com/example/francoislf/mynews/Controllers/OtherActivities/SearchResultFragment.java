package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.francoislf.mynews.Controllers.ItemClickSupport;
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
    private OnButtonClickedListener mCallback;

    @BindView(R.id.fragment_recyclerview_search) RecyclerView mRecyclerView;

    // Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener{void onButtonClicked(View view, String url);}

    public SearchResultFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.bind(this, view);
        configureRecyclerView();
        this.configureOnClickRecyclerView();
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

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_item_recyclerview)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ArticleItem articleItem = mAdapter.getArticle(position);
                        mCallback.onButtonClicked(v, articleItem.getWebUrl());
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    // Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
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
        if (!articleSearch.getResponse().getDocs().isEmpty()) {
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
                    else mArticleItem.setPhotoUrl("http://www.idfmoteurs.com/images/pas-image-disponible.png");
                    mArticleItem.setWebUrl(results.get(i).getWebUrl());

                    mArticleItemList.add(mArticleItem);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Response :")
                    .setMessage("No article found for your request\nPlease change filters and try again.")
                    .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                finalize();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }).create().show();
        }
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