package org.acme;

import io.smallrye.mutiny.Multi;

public class ReactivePlayground5 {

    public static void main(String[] args) throws InterruptedException {

        Multi<String> letters= Multi.createFrom().items("a", "b", "c");

        Multi<String> finalNumbers = letters.toHotStream().onCompletion().continueWith("1","2","3");

        finalNumbers.subscribe().with(System.out::println);

    }
}
