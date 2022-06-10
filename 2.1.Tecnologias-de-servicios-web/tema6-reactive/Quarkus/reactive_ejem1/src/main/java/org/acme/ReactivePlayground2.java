package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class ReactivePlayground2 {

    public static void main(String ... args) {
       
        Uni<String> uni1 = Uni.createFrom().item("one");
        Uni<String> uni2 = Uni.createFrom().item("two");
        Uni<String> uni3 = Uni.createFrom().item("three");
        
        Uni<String> anyUni = Uni.combine().any().of(uni1,uni2,uni3);        
        Multi<Integer> multi = Multi.createFrom().range(1, 10);

        anyUni.subscribe().with(System.out::println); // any
        Uni.combine().all().unis(uni1,uni2,uni3).asTuple().subscribe().with(t -> t.forEach(System.out::println)); // all
        
        multi.skip().first(3).subscribe().with(System.out::println);
        multi.skip().last(3).subscribe().with(System.out::println);
        
    }

}
