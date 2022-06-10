package org.acme;

import io.smallrye.mutiny.Multi;

public class ReactivePlayground7 {

    public static void main(String[] args) {

        Multi<Integer> data = Multi.createFrom().items(1,5,10)
            .flatMap(v -> Multi.createFrom().range(v, v+3));

        data.subscribe().with(System.out::println);

    }
}
