package com.zyl.generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class Test {

	public static void main(String[] args) {
		ExportDataModel<Aaa> t = new ExportDataModel<Aaa>();


		aaa(t.getClass());

		ExportDataModel r = bbb(SubBbbModel.class);
		System.out.println(r);
		
		
	}
	
	public static ExportDataModel<?> aaa(Class clazz) {
	
//		System.out.println(t.getTypeName());
		return null;
		
	}
	public static <T extends ExportDataModel> ExportDataModel bbb(Class<T> modelClass) {
		try {
			return modelClass.getDeclaredConstructor().newInstance();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
