package com.zyl.rxjava;

import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.parallel.ParallelFlowable;

public class Test {

	public static void main(String[] args) {
		List<Integer> codeList = new ArrayList<>();
		codeList.add(11);
		codeList.add(12);
		codeList.add(13);
		codeList.add(14);
		codeList.add(15);
		codeList.add(16);
		codeList.add(17);
		codeList.add(18);
		codeList.add(19);
		codeList.add(20);
		
		Flowable<Integer> f = Flowable.create(emitter -> {
            try {
                for (Integer code : codeList) {
                    Thread.sleep(100L);
                    emitter.onNext(code);
                }
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
		
		ParallelFlowable<Integer> pf = f.parallel();
		
		Consumer<Object> onNext = (p)->{
			System.out.println("------->" + p);
		};
		
		
//		pf.subscribe(onNext);
	}
	
	static Consumer<Object> next(Object p) {
		System.out.println("------->" + p);
		Consumer<Object> con = exportData -> {
			System.out.println("=====>" + p);
		};
		return con;
	}

}
