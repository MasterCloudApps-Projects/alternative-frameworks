package org.acme;

import java.time.Duration;

import io.smallrye.mutiny.Multi;

public class ReactivePlayground4 {

    public static void main(String[] args) throws InterruptedException {

       Multi<Long> nums = Multi.createFrom().ticks().every(Duration.ofMillis(500));

       nums.subscribe().with(System.out::println);

    }


}
