package com.qunar.flight.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class Solution {
    private Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Solution solution = new Solution();
        CompletableFuture<String> one = CompletableFuture.supplyAsync(() -> {
            System.out.println("1" + Thread.currentThread().getName());
            return "a";
        });
        one.thenAccept(res -> {
            System.out.println(res);
            System.out.println("2" + Thread.currentThread().getName());
        }).thenRun(() -> {
            System.out.println("3" + Thread.currentThread().getName());

        });

        String res = one.exceptionally(e -> "default").get();

        System.out.println(res);
        Thread.sleep(3000000);
    }
    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        return cut(s);
    }
    private int cut(String s) {
        if (map.get(s) != null) {
            return map.get(s);
        }
        int minCut = 99999999;
        for (int i = 1; i <= s.length(); i++) {
            if (!judge(s.substring(0, i))) {
                continue;
            }
            if (s.substring(i).length() == 0) {
                minCut = 0;
            } else {
                int tempCut = cut(s.substring(i));
                // 记忆
                map.put(s.substring(i), tempCut);
                minCut = Math.min(minCut, tempCut + 1);
            }
        }
        return minCut;
    }

    private boolean judge(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }
}
