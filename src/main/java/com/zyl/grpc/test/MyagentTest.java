package com.zyl.grpc.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.fastjson.JSONObject;

public class MyagentTest {
	public static void main(String[] args) {
		List<Widget> widgets = new ArrayList<>();
		{
			Widget w = new Widget();
			w.setColor("RED");
			w.setName(1);
			w.setWeight(100);
			widgets.add(w);
		}
		{
			Widget w = new Widget();
			w.setColor("RED");
			w.setName(2);
			w.setWeight(200);
			widgets.add(w);
		}
		{
			Widget w = new Widget();
			w.setColor("RED");
			w.setName(3);
			w.setWeight(120);
			widgets.add(w);
		}
		{
			int sum = widgets.stream().filter(b -> b.getColor() == "RED")
					.sorted((x, y) -> x.getWeight() - y.getWeight()).mapToInt((w) -> w.getWeight()).sum();
			System.out.println(sum);
		}


		Map<Integer, String> map = new HashMap<>();
		map.put(001, "001");
		map.put(002, "002");
		System.out.println(map);
		System.out.println("OKK" + 002);
		
		ReentrantLock reentrantLock = new ReentrantLock();
		try{
			//获取锁
			reentrantLock.tryLock(100, TimeUnit.SECONDS);
			System.out.println("获取锁");
			System.out.println("执行业务begin");
			Thread.sleep(1000L);
			System.out.println("执行业务end");
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("释放锁");
			//释放锁
			reentrantLock.unlock();
		}
		System.out.println("===========");
		
		JSONObject json = new JSONObject();
		long a = 124L;
		json.put("aaa", a);
		System.out.println(new BigDecimal(json.getString("aaa")));
		
		
	}
	static String md5(String s) {
		return null;
	}
}

class Widget {

	private Integer name;
	private String color;
	private Integer weight;

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
