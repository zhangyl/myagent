package com.zyl.easyrule.condition.client;

public class MyRuleOneImpl implements RuleOne {

	@Override
	public void setInput(InputPa pa) {
		pa.setEntCode(null);
		System.out.println("setInputPa");

	}

	@Override
	public void setInput(InputPb pb) {
		System.out.println("setInputPb");
	}

}
