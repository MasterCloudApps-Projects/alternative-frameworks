package org.acme;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class ReactivePlayground1 {

    public static void main(String ... args) throws InterruptedException, ExecutionException {

        Uni<Long> nullUni = Uni.createFrom().nullItem();
        Uni<Long> emptyUni = Uni.createFrom().nothing();
        Multi<Long> emptyMulti = Multi.createFrom().nothing();
        
        nullUni.subscribe().with(System.out::println);
        emptyUni.subscribe().with(System.out::println);
        emptyMulti.subscribe().with(System.out::println);
        
        Uni<String> uni1 = Uni.createFrom().item("one");
        Uni<String> uni2 = Uni.createFrom().item("two");
        Uni<String> uni3 = Uni.createFrom().item("three");

        // Blocking

        System.out.println(uni1.subscribeAsCompletionStage().get());
        System.out.println(uni2.subscribeAsCompletionStage().get());
        System.out.println(uni3.subscribeAsCompletionStage().get());        

        Uni<List<String>> uniList = Uni.join().all(uni1,uni2,uni3).andFailFast();
        Multi<List<String>> multiList = uniList.toMulti();

        Multi<String> multi = Multi.createFrom().items("1","2","3");
        Uni<List<String>> unitList2 = multi.collect().asList();

        uni1.subscribe().with(System.out::println);
        multi.subscribe().with(System.out::println);

        uniList.subscribe().with(System.out::println);
        multiList.subscribe().with(System.out::println);

        Uni.join().all(uniList, unitList2).andFailFast().subscribe().with(System.out::println);

        // Order matters

        Uni.join().all(unitList2, uniList).andFailFast().subscribe().with(System.out::println);
        
        
    }

}
