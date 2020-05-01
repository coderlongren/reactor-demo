package com.qunar.flight.user.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

public class TestFlux {
    public static void main(String[] args) throws InterruptedException {
        Flux.just("tom")
                .map(s -> {
                    System.out.println("[map] Thread name: " + Thread.currentThread().getName());
                    return s.concat("@mail.com");
                })
                .publishOn(Schedulers.newElastic("thread-publishOn"))
                .filter(s -> {
                    System.out.println("[filter] Thread name: " + Thread.currentThread().getName());
                    return s.startsWith("t");
                })
                .subscribeOn(Schedulers.newElastic("thread-subscribeOn"))
                .subscribe(s -> {
                    System.out.println("[subscribe] Thread name: " + Thread.currentThread().getName());
                    System.out.println(s);
                });

        Flux.empty().subscribe(System.out::println);

//        Flux.error(new RuntimeException()).subscribe(System.out::println);

        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(2));

        Mono mono = Mono.create(monoSink -> {
            String res = call();
            monoSink.success(res);
        });
        mono = mono.map(res -> {
            System.out.println(" 线程名字是:" + Thread.currentThread().getName());
            System.out.println("现在的结果是" + res);
            return res;
        });

        Flux.just("1", "2").reduce((a, b) -> a + b).subscribe(System.out::println);
        Flux.create(sink -> {
            System.out.println("执行线程名:" + Thread.currentThread().getName());
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sink.next(i);
            }
            sink.complete();
            // 这个其实是不会触发的、
            sink.next(2);
        }).subscribeOn(Schedulers.elastic()).subscribe(item -> {
            System.out.println("执行线程名:" + Thread.currentThread().getName());
            System.out.println("结果是 :" + item);
        });

        Thread.sleep(Integer.MAX_VALUE);

    }
    private static String call() {
        System.out.println("发起远程调用");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "结果";
    }
}
