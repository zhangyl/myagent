package com.zyl.easyrule;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.googlecode.aviator.AviatorEvaluator;
import com.zyl.easyrule.tool.Calculator;

class A {
	private List<Map<String, String>> list = new ArrayList<>();
	private String name;
	public List<Map<String, String>> getList() {
		return list;
	}
	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		
		return JSON.toJSONString(this);
	}
	
}
public class Test {
	public static RulesEngine rulesEngine = new DefaultRulesEngine();
	
	public static void main(String[] args) throws Exception {
		
		List<Map<String,String>> list = new LinkedList<>();
		Map<String,String> map = new HashMap<>();
		map.put("11", "11");
		map.put("33", "11");
		map.put("22", "11");
		list.add(map);
		
		A a = new A();
		a.setList(list);
		a.setName("zyl");
		String jsonA = a.toString();

		String jsonB = "";
		try {
			JSONObject j = JSON.parseObject(jsonB);
			System.out.println(j == null);
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println(JSONValidator.from(jsonB).validate());
		
		String aaa = "{\"city\": \"\", \"province\": \"\", \"codeConfirm\": \"\", \"consumeTime\": 1669992866438, \"machineCode\": \"\", \"numberConfirm\": \"\", \"consumeEndDate\": 1669992866438, \"consumeStartDate\": 1669992866438}";
		if(JSONValidator.from(aaa).validate()) {
			JSONObject json = JSON.parseObject(aaa);
//			System.out.println(json);
			String jsonStr = json.toString();
			System.out.println(jsonStr);
		}
		
	}
	
	public static void test3() {

		
		Map<String, Boolean> allResult = new HashMap<>();
		allResult.put("myRuleA", true);
		allResult.put("myRuleB", false);
		allResult.put("myRuleC", true);
        String expression = "( myRuleA || myRuleB) && myRuleC";
		try {
			calculator.Start(allResult);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	a&b|c^xxx,xxx2
	public static boolean test2() throws Exception {
		Map<String, Object> allResult = new HashMap<>();
		allResult.put("myRuleA", true);
		allResult.put("myRuleB", false);
		allResult.put("myRuleC", true);
        String expression = "( myRuleA || myRuleB) && myRuleC";
		Boolean r = (Boolean)AviatorEvaluator.execute(expression, allResult);
		return r;
	}
	
	public static Boolean test() throws Exception {
		
		// create rules
        Rules rules = new Rules();
        
//        {
//        	MyRule rule = new MyRule();
//        	rules.register(rule);
//        }
        {
        	MyRuleA rule = new MyRuleA();
        	rules.register(rule);
        }
        {
        	MyRuleB rule = new MyRuleB();
        	rules.register(rule);
        }
        {
        	MyRuleC rule = new MyRuleC();
        	rules.register(rule);
        }

        // create a rules engine and fire rules on known facts
//        RulesEngine rulesEngine = new DefaultRulesEngine();
        
        // create facts
        Facts facts = new Facts();
        facts.put("zyl", "zyl value");

        Map<Rule, Boolean> result = rulesEngine.check(rules, facts);

//        System.out.println(result.containsValue(true));
        Map<String, Boolean> allResult = new HashMap<>();
        Map<String, Object> allResult2 = new HashMap<>();
        for(Entry<Rule, Boolean> entry : result.entrySet()) {
        	allResult.put(entry.getKey().getName(), entry.getValue());
        	allResult2.put(entry.getKey().getName(), entry.getValue());
        }

		
//        rulesEngine.fire(rules, facts);
        String expression = "(myRuleA || myRuleB) && myRuleC";

        StringReader strReader2 = new StringReader(expression);
        Calculator parser2 = new Calculator(strReader2);

        boolean flag = parser2.Start(allResult);

//        Boolean flag = (Boolean) AviatorEvaluator.execute(expression, allResult2);
//                System.out.println("flag = " + flag);
        return flag;
       
	}

}
