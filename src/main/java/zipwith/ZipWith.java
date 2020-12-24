package zipwith;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;

public class ZipWith {

    public static void main(String[] args) {
        var word = Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");
        Observable.fromIterable(word)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d.%s", count, string)).subscribe(System.out::println); // an event for each word

        Observable. fromIterable(word)
                .flatMap(w -> Observable.fromArray(w.split("")))
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println); // Flatmap allow us to emmit an event foreach letter in this case




        Observable.fromIterable(word)
                .flatMap(w -> Observable.fromArray(w.split("")))
                .distinct()
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                   .subscribe(System.out::println); // It have the same above, but add a distinct processing in the pipeline


        Observable.fromIterable(word)
                .flatMap(w -> Observable.fromArray(w.split("")))
                .distinct()
                .sorted()
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println); /// It have the same above, but add a sorted processing in the pipeline
    }
}
