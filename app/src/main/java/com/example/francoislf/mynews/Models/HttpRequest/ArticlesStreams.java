package com.example.francoislf.mynews.Models.HttpRequest;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticlesStreams {

    public static Observable<TopStories> streamTopStories(String section){
        ArticlesService articlesService = ArticlesService.retrofit.create(ArticlesService.class);
        return articlesService.getTopStoriesRequest(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MostPopular> streamMostPopular(){
        ArticlesService articlesService = ArticlesService.retrofit.create(ArticlesService.class);
        return articlesService.getMostPopularRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleSearch> streamArticleSearch(String text, String start, String end){
        ArticlesService articlesService = ArticlesService.retrofit.create(ArticlesService.class);
        return articlesService.getArticleSearch(text, start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

}
