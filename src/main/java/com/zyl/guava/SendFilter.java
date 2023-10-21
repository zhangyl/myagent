package com.zyl.guava;

public interface SendFilter {
	default boolean filter(Column col)  {
		return false;
	}
}
