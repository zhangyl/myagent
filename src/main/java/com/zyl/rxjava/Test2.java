package com.zyl.rxjava;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2 {
	public final static ExecutorService executorService =
	        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), runnable -> {
	            Thread thread = new Thread(runnable);
	            thread.setDaemon(true);
	            return thread;
	        });
	public static void main(String[] args) {
		final CountDownLatch countDownLatch = new CountDownLatch(5);
		for(int i = 0; i < 5; ++i) {
			executorService.submit(()->{
				try {
					Thread.sleep(5000L);
					System.out.println(Thread.currentThread().getName());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		try {
			
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("OKKKKKK");
	}

}
