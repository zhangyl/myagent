package com.zyl.jvm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	static int count = 0;
	static ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(8);
	
	public static void main(String[] args) throws Exception {
		Thread.sleep(15000L);
		long b = System.currentTimeMillis();
		for (;;) {
			newCachedThreadPool.execute(() -> {
				invoke(4*8);
				invoke(3*8);

			});
			newCachedThreadPool.execute(() -> {
				invoke(4*1024);
				invoke(3*1024);
				
			});
			newCachedThreadPool.execute(() -> {
				invoke(2*1024);
				invoke(3*1024);
			});
			newCachedThreadPool.execute(() -> {
				invoke(5*1024);
				invoke(6*1024);
				invoke(8);
				invoke(1*1024);
			});
			newCachedThreadPool.execute(() -> {
				invoke(8);
				invoke(1024);
			});
			newCachedThreadPool.execute(() -> {
				invoke(8);
				invoke(4*1024);
			});
			newCachedThreadPool.execute(() -> {
				invoke(8);
				invoke(2*1024);
			});
			newCachedThreadPool.execute(() -> {
				invoke(1*1024);
				invoke(3*1024);
				invoke(8);
				invoke(7);
			});
			
			++count;
			
			if(count % 10000 == 0) {
				Thread.sleep(500L);
			}
//			if(count > 1000000) {
//				System.out.println("---------------break------------------");
//				break;
//			}
//			long e = System.currentTimeMillis();
//			System.out.println("count = " + ++count + ", time=" + (e-b));
//			Thread.sleep(1L);
		}
		
//		Thread.sleep(10000000000000L);

	}
	
	public static int invoke(int a) {
		
		byte[] b = new byte[1024 * a];
		if (b.length > 300) {
			b[300] = 1;
		}
		String s = new String(b);
		int r = s.length();
		try {
//			Thread.sleep(20L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

}
