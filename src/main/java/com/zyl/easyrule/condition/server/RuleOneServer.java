package com.zyl.easyrule.condition.server;

import java.util.ArrayList;
import java.util.List;

import com.zyl.easyrule.condition.client.InputPa;
import com.zyl.easyrule.condition.client.InputPb;

public class RuleOneServer {
	public List<Object> getInputParamList() {
		List<Object> list = new ArrayList<>();
		list.add(new InputPa());
		list.add(new InputPb());
		return list;
	}
}
