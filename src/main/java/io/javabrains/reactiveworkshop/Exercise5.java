package io.javabrains.reactiveworkshop;

import java.io.IOException;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class Exercise5 {

  public static void main(String[] args) throws IOException {

    // Use ReactiveSources.intNumberMono() and ReactiveSources.userMono()

    // Subscribe to a flux using the error and completion hooks
//    ReactiveSources.intNumbersFlux().subscribe(number -> System.out.println(number),
//        throwable -> System.out.println(throwable.getMessage()),
//        () -> System.out.println("Completed!"));

//    Disposable subscribe = ReactiveSources.intNumbersFlux().subscribe(
//        number -> System.out.println(number),
//        err -> System.out.println("Error: " + err.getMessage()),
//        () -> System.out.println("Completed!")
//    );
    // Subscribe to a flux using an implementation of BaseSubscriber
    ReactiveSources.intNumbersFlux().subscribe(new MySubscriber<>());

    System.out.println("Press a key to end");
    System.in.read();
  }

}

class MySubscriber<T> extends BaseSubscriber<T> {

  public void hookOnSubscribe(Subscription subscription) {
    System.out.println("Subscribed!");
    request(1); // Request 2 items at a time
  }

  @Override
  protected void hookOnNext(T value) {
    System.out.println("Received: " + value);
    request(1);
  }

  @Override
  protected void hookOnError(Throwable throwable) {
    System.out.println("Error occurred: " + throwable.getMessage());
  }

  @Override
  protected void hookOnComplete() {
    System.out.println("Subscription completed!");
  }
}