package com.liuxw.juc;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author  XiaoWen
 * Date  2019/11/25 11:42
 * Version 1.0
 */
public class JucTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ArrayList<Future<Long>> list = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int m = 0; m < 3; m++) {
            //submit内部是一个线程完成的任务，外部的for循环是开启几个线程并发执行任务
            Future<Long> submit = pool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(100);
                return System.currentTimeMillis()/1000;
            });
            list.add(submit);
        }
        Thread.sleep(1000);
        System.out.println(list.size());
        //获取线程任务结果里的数据
        Long aLong = list.get(0).get();
        System.out.println(aLong);
        pool.shutdown();
    }

    public static void main2(String[] args) throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int m = 0; m < 3; m++) {
            //submit内部是一个线程完成的任务，外部的for循环是开启几个线程并发执行任务
            pool.submit(() -> {
                for (int i = 0; i < 3; i++) {
//                System.out.println(Thread.currentThread());
                    list.add(i);
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        Thread.sleep(1000);
        System.out.println(list.size());
        pool.shutdown();
    }
}
