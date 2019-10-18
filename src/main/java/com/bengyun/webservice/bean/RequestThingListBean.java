package com.bengyun.webservice.bean;

import java.util.List;

public class RequestThingListBean {
	
	public RequestThingListBean() {}
	public RequestThingListBean(List<Thing> things) {
		this.things = things;
	}
	
	private List<Thing> things;

	public List<Thing> getThings() {
		return things;
	}
	public void setThings(List<Thing> things) {
		this.things = things;
	}
	
	public static class Thing {
		public String thing_id;
		public String thing_type;
	}
}
