package com.zyl.guava;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class GuavaDemo {

	public static void main(String args[]) throws Exception {
		List<Column> columnList = new ArrayList<>();
		{
			Column c = new Column();
			c.setName("user_name");
			c.setValue("zyl");
			columnList.add(c);
		}
		{
			Column c = new Column();
			c.setName("user_name");
			c.setValue("wqc");
			columnList.add(c);
		}
		{
			Column c = new Column();
			c.setName("user_name");
			c.setValue("yhw");
			columnList.add(c);
		}
		System.out.println("before => " + JSON.toJSONString(columnList));
		List<Column> result = ProxyHandler.execute(columnList);
		System.out.println("after  => " + JSON.toJSONString(result));
	}

}
