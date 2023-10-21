package com.zyl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class Test2 {
	private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable runnable) {
			Thread thread = new Thread(runnable);
			thread.setDaemon(true);
			return thread;
		}
	});

	private static String localAddress;
	private static int i = 0;

	public static void main(String[] args) throws Exception {
		AtomicInteger count = new AtomicInteger(0);
		count.addAndGet(1);
		count.addAndGet(1);
		for (int i = 0; i < count.get(); ++i) {
			System.out.println("========设置local address 开始==========");

		}

		List<String> list = new ArrayList<>();

		list.add(null);
		list.add("2");
		list.add("1");

		List<String> r = list.stream().filter(item -> item != null).collect(Collectors.toList());

		for (String item : r) {
			System.out.println(item);
		}

	}

}
