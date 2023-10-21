package com.zyl.guava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;



public class ProxyHandler {
	public static List<Column> execute(final List<Column> columnList) {
		List<Column> filterResult = new ArrayList<>();
		if(columnList == null) {
			return filterResult;
		}
		
		ServiceLoader<SendFilter> service = ServiceLoader.load(SendFilter.class);
		Iterator<SendFilter> filterIterator = service.iterator();

		for(Column c : columnList) {
			boolean filtered = false;
			while(filterIterator.hasNext()) {
				SendFilter sendFilter = filterIterator.next();
				if(sendFilter.filter(c)) {
					filtered = true;
					break;
				}
			}
			if(!filtered) {
				filterResult.add(c);
			}
		}
		return filterResult;
	}
}
