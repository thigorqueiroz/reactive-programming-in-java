package expensive;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExpensiveComputation {

    public static void main(String[] args) throws InterruptedException {
        Flowable.fromCallable(
                () -> {
                    Thread.sleep(1000);
                    return "done";
                }
        ).subscribeOn(Schedulers.io())
         .observeOn(Schedulers.single())
         .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(20000);// Stop main thread to avoid stop all background computation
    }

}
