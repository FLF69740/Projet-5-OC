package com.example.francoislf.mynews.Controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.francoislf.mynews.Models.ArticlesStreams;
import com.example.francoislf.mynews.Models.MostPopular;
import com.example.francoislf.mynews.Models.TopStories;
import com.example.francoislf.mynews.R;

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
    @BindView(R.id.fragment_page_title) TextView mTextView;

    public static final String KEY_TITLE = "KEY_TITLE";
    private String mIndexArticles = "";

    private Disposable mDisposable;

    public PageFragment() {
        // Required empty public constructor
    }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);

        getArticles(getArguments().getString(KEY_TITLE,""));

        mTextView.setText(mIndexArticles);
        mLinearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        return view;
    }

    public void getArticles(String indexArticles){

        switch (indexArticles){
            case "Top Stories":
                executeHttpRequestTopStories();
                break;
            case "Most Popular" :
                executeHttpRequestMostPopular();
                break;
                default:
                    break;
        }

    }

    /**
     *  MEMORY lEAKS PROTECTION
     */

    private void disposeWhenDestroy(){
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.disposeWhenDestroy();
    }

    /**
     *  HTTP (RxJAVA)
     */

    // 1 méthode this.disposable pour topStories
    private void executeHttpRequestTopStories(){
        this.mDisposable = ArticlesStreams.streamTopStories()
                .subscribeWith(new DisposableObserver<TopStories>() {
                    @Override
                    public void onNext(TopStories topStories) {
                        mTextView.setText(getUpdateUITopStories(topStories));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGO", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAGO", "TopStories streams on complete");
                        mTextView.setText("ERROR : page uploading failed");
                    }
                });
    }

    // 1 méthode "générique" de retour de string
    private String getUpdateUITopStories(TopStories topStories){
        StringBuilder stringBuilder = new StringBuilder();

        List<TopStories.Result> results = topStories.getResults();
        for (int i = 0 ; i < results.size() ; i++) stringBuilder.append("- " + results.get(i).getTitle() + "\n");

        return stringBuilder.toString();
    }

    // 2 Méthode pour mostPopular
    private void executeHttpRequestMostPopular(){
        this.mDisposable = ArticlesStreams.streamMostPopular()
                .subscribeWith(new DisposableObserver<MostPopular>() {
                    @Override
                    public void onNext(MostPopular mostPopular) {
                        mTextView.setText(getUpdateUIMostPopular(mostPopular));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGO", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAGO", "Most popular streams on complete");
                        mTextView.setText("ERROR : page uploading failed");
                    }
                });
    }

    // 1 méthode "générique" de retour de string
    private String getUpdateUIMostPopular(MostPopular mostPopular){
        StringBuilder stringBuilder = new StringBuilder();

        List<MostPopular.Result> results = mostPopular.getResults();
        for (int i = 0 ; i < results.size() ; i++) stringBuilder.append("- " + results.get(i).getTitle() + "\n");

        return stringBuilder.toString();
    }



}
