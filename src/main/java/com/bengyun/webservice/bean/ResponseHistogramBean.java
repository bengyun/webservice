package com.bengyun.webservice.bean;

public class ResponseHistogramBean {
	
	public ResponseHistogramBean() {}
	public ResponseHistogramBean(
			int value,
			float le) {
		this.value = value;
		this.le = le;
	}
	
	public int value;
	public float le;
	
}
