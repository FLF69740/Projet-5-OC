package com.example.francoislf.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.example.francoislf.mynews.Models.ArticlesStreams;
import com.example.francoislf.mynews.Models.MostPopular;
import com.example.francoislf.mynews.Models.TopStories;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(AndroidJUnit4.class)
public class FonctionnalTestsHttpRequest {

    @Test
    public void streamTopStoriesTest() throws Exception{
        Observable<TopStories> observable = ArticlesStreams.streamTopStories();
        TestObserver<TopStories> testObserver = new TestObserver<>();

        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStories topStories = testObserver.values().get(0);

        assertThat("Result must be not null", topStories.getResults().get(0).getTitle().toString() != null);
    }

    @Test
    public void streamMostPopularTest() throws Exception{
        Observable<MostPopular> observable = ArticlesStreams.streamMostPopular();
        TestObserver<MostPopular> testObserver = new TestObserver<>();

        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        MostPopular mostPopular = testObserver.values().get(0);

        assertThat("Result must be not null", mostPopular.getResults().get(0).getTitle().toString() != null);
    }


}
