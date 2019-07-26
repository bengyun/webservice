package com.bengyun.webservice.bean;

import java.util.List;

public class RequestThingListBean {
	
	public RequestThingListBean() {}
	public RequestThingListBean(List<String> things) {
		this.things = things;
	}
	
	private List<String> things;

	public List<String> getThings() {
		return things;
	}
	public void setThings(List<String> things) {
		this.things = things;
	}
}
