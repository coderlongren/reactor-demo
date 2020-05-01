package com.qunar.flight.user.test;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Mono mono = test();
        mono.subscribe(res -> {
            System.out.println(res);
        });
        System.out.println("ddd");

        User u = new User(2);

        Mono<User> mono1 = Mono.defer(() -> {
            u.setAge(sss());
            return Mono.just(u);
        });
        Thread.sleep(2000);
        u.setAge(5);
        mono1.subscribeOn(Schedulers.elastic());
        u.setAge(6);
        System.out.println("fdgdf");
        mono1.subscribe(item -> {
                    System.out.println(item.getAge());
                });
        mono1.block();

    }
    private static  int sss() {
        System.out.println("开始....");
        return 2;
    }
    public static  Mono test() {
        System.out.println("asas");
        return Mono.defer(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Mono.just("sss");
        }).subscribeOn(Schedulers.elastic());

    }
    static class User {
        Integer age;

        public User(Integer age) {
            this.age = age;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
