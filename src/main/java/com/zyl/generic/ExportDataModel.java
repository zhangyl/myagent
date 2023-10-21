package com.zyl.generic;

import java.util.List;

public class ExportDataModel<T> {
	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
