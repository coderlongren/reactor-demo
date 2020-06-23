package com.qunar.flight.user.util;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SampleRx {
    private static final Logger Log = LoggerFactory.getLogger(SampleRx.class);
    private static final String TAG = "tag";

    private static final ExecutorService POOL = Executors.newFixedThreadPool(10, l -> new Thread(l, "自定义线程池"));

    private static final ExecutorService POOL2 = Executors.newFixedThreadPool(10, l -> new Thread(l, "第二个线程池"));


    private static Mono getAndSet(String a, String b) {
        return Mono.defer(() -> Mono.just((rpcCall(a, b)))
                .subscribeOn(reactor.core.scheduler.Schedulers.fromExecutor(POOL2))
                .timeout(Duration.ofSeconds(1))
                .doOnSuccess(item -> {
                    System.out.println("成功" + item);
                })
                .doOnError(e -> {
                    System.out.println(e);
                }));
    }
    public static void maindddd(String[] args) throws InterruptedException {
        //创建一个上游 Observable：
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//            }
//        });
//        //创建一个下游 Observer
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.info(TAG, "" + d);
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.info(TAG, "" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.info(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.info(TAG, "complete");
//            }
//        };
        //建立连接
//        observable.subscribe(observer);

        List<String> ipList = new ArrayList<String>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }
        // 2.顺序调用
        long start = System.currentTimeMillis();
        Flowable c = Flowable.fromArray(ipList.toArray(new String[0]))
                .map(v -> {
                    System.out.println("发射事件 线程 :" + Thread.currentThread().getName());
                    return Flowable.just(v)
                            .subscribeOn(Schedulers.io())
                            .map(item -> rpcCall(item, item));
                }).subscribeOn(Schedulers.from(POOL))
                .map(item -> {
                    System.out.println("Map 执行线程 :" + Thread.currentThread().getName());
                    return item;
                });

        System.out.println("哈哈");
        Thread.sleep(3000);
        System.out.println("哈哈");

        c.blockingFirst();
        // 3.打印耗时
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        Thread.sleep(400000);

    }

    public static void main2(String[] args) throws InterruptedException {
        List<Observable<String>> observables = Stream.of("1", "2", "3").map(item -> Observable.create((ObservableEmitter<String> emitter) -> {
                    emitter.onNext(rpcCall(item, "sss"));
                    emitter.onComplete();
                }).subscribeOn(Schedulers.io())
        ).collect(Collectors.toList());
        System.out.println("开始");
        Thread.sleep(3000);
        Observable.zip(observables, res -> {
            for (Object ip : res) {
                System.out.println((String) ip);
            }
            return "";
        }).blockingFirst();
        Thread.sleep(3000);
    }

    public static String rpcCall(String ip, String param) {
        System.out.println(Thread.currentThread().getName() + " " + ip + " rpcCall:" + param);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ip;
    }

    public static void main4(String[] args) throws InterruptedException {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                System.out.println("" + Thread.currentThread().getName());
                return Observable.fromIterable(list).delay(1, TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
        Thread.sleep(4000000);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = Mono.defer(() -> {
            System.out.println("Thread name + " + Thread.currentThread().getName());
            return Mono.fromCallable(() -> speed());
        }).subscribeOn(reactor.core.scheduler.Schedulers.elastic())
                .timeout(Duration.ofSeconds(2))
                .doOnSuccess(res -> System.out.println("成功记录"))
                .onErrorResume(err -> {
                    System.out.println("Error" + err);
                    return Mono.empty();
                })
                .toFuture();

        System.out.println("主线程不阻塞");
        System.out.println(future.get());

    }
    private static int speed() {
        try {
            System.out.println("Thread name + " + Thread.currentThread().getName());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        throw new RuntimeException("IOyichnag ");
    }

//    public static String sortString(String s) {
//        char[] chars = s.toCharArray();
//        Map<Character, Inter>
//    }

}
