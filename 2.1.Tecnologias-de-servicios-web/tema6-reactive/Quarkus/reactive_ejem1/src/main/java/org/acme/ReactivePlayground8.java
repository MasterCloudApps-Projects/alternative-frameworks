package org.acme;

import io.smallrye.mutiny.Multi;

public class ReactivePlayground8 {

    public static void main(String[] args) {

        Multi<Integer> firsNumbers = Multi.createFrom().items(1, 2, 3)
            .flatMap(v -> sumService(v));
        
        Multi<Integer> lastNumbers = Multi.createBy().merging().streams(firsNumbers,Multi.createFrom().items(4,5,6));
        
        lastNumbers.subscribe().with(System.out::println);
            
    }
    
    private static Multi<Integer> sumService(Integer v) {
    
        if (v != 3) {
            return Multi.createFrom().item(v+1);
        } else {
            return Multi.createFrom().nothing();
        }
    }
}
