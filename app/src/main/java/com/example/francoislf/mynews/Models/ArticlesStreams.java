package com.example.francoislf.mynews.Models;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticlesStreams {

    public static Observable<TopStories> streamTopStories(){
        ArticlesService articlesService = ArticlesService.retrofit.create(ArticlesService.class);
        return articlesService.getTopStoriesRequest()
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

}
