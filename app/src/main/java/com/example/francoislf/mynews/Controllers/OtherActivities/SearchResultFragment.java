package com.example.francoislf.mynews.Controllers.OtherActivities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.francoislf.mynews.Models.DateFormatTransformer;
import com.example.francoislf.mynews.Models.HttpRequest.ArticleSearch;
import com.example.francoislf.mynews.Models.HttpRequest.ArticlesStreams;
import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
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

    SearchPreferences mSearchPreferences;
    Disposable mDisposable;

    @BindView(R.id.search_result_fragment_editText)
    TextView mTextView;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        ButterKnife.bind(this, view);

        return view;
    }


    public void updateFragmentData(SearchPreferences searchPreferences){

        this.mSearchPreferences = searchPreferences;

        this.executeHttpRequest(mSearchPreferences.getSearchString(),
                new DateFormatTransformer(mSearchPreferences.getBeginDateString()).getDateStringOutput(),
                new DateFormatTransformer(mSearchPreferences.getEndDateString()).getDateStringOutput());
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
                        mTextView.setText(getUpdateUI(articleSearch));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGO", "On Error " + Log.getStackTraceString(e));
                        mTextView.setText("ERROR : page uploading failed");
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
    private String getUpdateUI(ArticleSearch articleSearch){
        StringBuilder stringBuilder = new StringBuilder();

        List<ArticleSearch.Doc> results = articleSearch.getResponse().getDocs();
        for (int i = 0 ; i < results.size() ; i++) stringBuilder.append("- " + results.get(i).getSnippet() + "\n");

        if (stringBuilder.toString().isEmpty()) stringBuilder.append("NO ARTICLES FOUND\n\n TRY AN OTHER CONFIGURATION.");

        return stringBuilder.toString();
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

}
