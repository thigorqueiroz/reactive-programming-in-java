package collection;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;

public class ObservableCollection {

    public static void main(String[] args) {
        var x = Arrays.asList("T", "H", "I", "A", "G", "O");

        Observable.just(x).subscribe(System.out::println); // A single event that contains whole list

        Observable.fromIterable(x).subscribe(System.out::println); //An event per Item
    }
}
