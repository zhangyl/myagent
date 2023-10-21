package com.zyl.rxjava;

import java.util.List;

import org.reactivestreams.Subscription;

import com.alibaba.fastjson.JSON;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Consumer;

public class Test3 {
	static int count = 0;
	static int times = 0;
	public static void main(String[] args) throws Exception {
		Flowable<Integer> infFlow = Flowable.create(new FlowableOnSubscribe<Integer>() {
			@Override
			public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
				for(times=0; times<10; times++) {
	
					for(int i=0; i<3; i++) {
						emitter.onNext(++count);
						System.out.println("emit " + count);
					}
					System.out.println("========");
				}
	
				emitter.onComplete();
			}
		}, BackpressureStrategy.BUFFER);
		
		Flowable<List<Integer>> listFlow = infFlow.buffer(3);
		listFlow.subscribe(new Consumer<List<Integer>>() {

			@Override
			public void accept(List<Integer> value) throws Exception {
				System.out.println("result = " + JSON.toJSONString(value));
			}});
//		listFlow.subscribe(new FlowableSubscriber<List<Integer>>() {
//			@Override
//			public void onSubscribe(Subscription subscription) {
//				subscription.request(30);
//				System.out.println("subscription = " + subscription);
//			}
//
//			@Override
//			public void onNext(List<Integer> value) {
//				// 可以处理返回的值
//				System.out.println("result = " + value.size());
//			}
//
//			@Override
//			public void onError(Throwable throwable) {
//				throwable.printStackTrace();
//			}
//
//			@Override
//			public void onComplete() {
//				System.out.println("onComplete");
//			}
//		});
		
//		Thread.sleep(100000L);
	}

}
