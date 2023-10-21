package com.zyl.guava;

public class MySendFilter implements SendFilter {

	@Override
	public boolean filter(Column col) {
		if("user_name".equals(col.getName())) {
			return "zyl".equals(col.getValue());
		}
		return false;
	}

}
