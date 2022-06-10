package org.acme;

import java.util.concurrent.atomic.AtomicInteger;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class ReactivePlayground3 {

    public static void main(String ... args) {

        AtomicInteger atomic = new AtomicInteger();
        
        Multi<Integer> multi = Multi.createBy().repeating().uni(() -> Uni.createFrom().item(atomic.getAndIncrement())).atMost(10);

        multi.select().first(3).subscribe().with(System.out::println);
        multi.select().last(3).subscribe().with(System.out::println);
    }

}
