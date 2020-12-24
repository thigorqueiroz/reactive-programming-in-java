package zipwith;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZipWithTest {

    @Test
    public void testInSameThread() {
        //given
        var result = new ArrayList<>();
        Observable<String> observable = Observable.
                fromIterable(Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"))
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, index) -> String.format("%2d. %s",
                        index, string));

        //when
        observable.subscribe(result::add);
        //then
        assertThat(result, notNullValue());
        assertThat(result, hasItem(" 4. fox"));
    }

    @Test
    public void testUsingTestObserver() {

        //given
        var obs = new TestObserver<String>();
        Observable<String> observable = Observable.
                fromIterable(Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"))
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, index) -> String.format("%2d. %s",
                                index, string));

        //when
        observable.subscribe(obs);

        //then
        obs.assertComplete();
        obs.assertNoErrors();
        obs.assertValueCount(9);
        assertThat(obs.values(), hasItem(" 4. fox"));
    }

    @Test
    public void testFailure() {
        //given
        var obs = new TestObserver<String>();
        var ex =  new RuntimeException("boom!!");

        Observable<String> observable = Observable.
                fromIterable(Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"))
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, index) -> String.format("%2d. %s",
                                index, string)).concatWith(Observable.error(ex));

        //when
        observable.subscribe(obs);

        //then
        obs.assertError(ex);
        obs.assertNotComplete();
    }

    @Test
    public void testUsingComputationScheduler() {

        //given
        var obs = new TestObserver<String>();
        Observable<String> observable = Observable.
                fromIterable(Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"))
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, index) -> String.format("%2d. %s",
                                index, string));

        //when
        observable.subscribeOn(Schedulers.computation()).subscribe(obs);
        await().timeout(2, SECONDS).until(() -> obs.values().size(), equalTo(9));

        //then
        obs.assertComplete();
        obs.assertNoErrors();
        obs.assertValueCount(9);
        assertThat(obs.values(), hasItem(" 4. fox"));
    }
}
