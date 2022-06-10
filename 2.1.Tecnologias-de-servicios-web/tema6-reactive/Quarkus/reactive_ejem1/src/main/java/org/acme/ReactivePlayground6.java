package org.acme;

import io.smallrye.mutiny.Multi;

public class ReactivePlayground6 {

    public static void main(String[] args) throws InterruptedException {

        Multi<String> letters = Multi.createFrom().items("a", "b", "c")
            .onItem().invoke(l -> System.out.print(" l"+l));

        Multi<String> numbers = Multi.createFrom().items("1", "2", "3")
            .onItem().invoke(n -> System.out.print(" n"+n));

        letters.toHotStream();

        numbers.subscribe().with(f -> System.out.print(" f"+f));

    }
}
