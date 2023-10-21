//package com.zyl.easyrule;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.jeasy.rules.api.Facts;
//import org.jeasy.rules.api.Rule;
//import org.jeasy.rules.support.CompositeRule;
//
//import com.googlecode.aviator.AviatorEvaluator;
//
//public class SelfComposeRule extends CompositeRule {
//	public SelfComposeRule() {
//		this.name = "SelfComposeRule";
//		this.addRule(new MyRuleA());
//		this.addRule(new MyRuleB());
//		this.addRule(new MyRuleC());
//	}
//	@Override
//	public boolean evaluate(Facts facts) {
//        if (!rules.isEmpty()) {
//        	Map<String, Object> resultMap = new HashMap<>();
//            for (Rule rule : rules) {
//            	resultMap.put(rule.getName(), rule.evaluate(facts));
//            }
//            //动态编译这个表达式
//            String expression = "(myRuleA && myRuleB) && myRuleC";
//            Boolean flag = (Boolean) AviatorEvaluator.execute(expression, resultMap, true);
//            return flag;
//        }
//		return false;
//	}
//
//	@Override
//	public void execute(Facts facts) throws Exception {
//		// TODO Auto-generated method stub
//
//	}
//
//}
