package com.example.francoislf.mynews.Models;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticlesService {

    @GET("topstories/v2/home.json?api-key=279b7fb76917401da38ba9a87dfb4c84")
    Observable<TopStories> getTopStoriesRequest();

    @GET("mostpopular/v2/mostviewed/all-sections/7.json?api-key=279b7fb76917401da38ba9a87dfb4c84")
    Observable<MostPopular> getMostPopularRequest();


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
