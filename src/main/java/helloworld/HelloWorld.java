package helloworld;

import io.reactivex.rxjava3.core.Flowable;

public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hi guys, im an assync flow").subscribe(System.out::println);
    }
}
