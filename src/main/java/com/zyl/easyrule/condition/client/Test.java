package com.zyl.easyrule.condition.client;

import java.lang.reflect.Method;
import java.util.List;

import com.zyl.easyrule.condition.server.RuleOneServer;

public class Test {

	public static void main(String[] args) throws Exception {
		
		MyRuleOneImpl one = new MyRuleOneImpl();
		
		
		RuleOneServer server = new RuleOneServer();
		List<Object> inputParamList = server.getInputParamList();
		
		for(Object o : inputParamList) {
			Method method = one.getClass().getMethod("setInput", o.getClass());
			method.invoke(one, o);
		}
		System.out.println(inputParamList);
	}

}
