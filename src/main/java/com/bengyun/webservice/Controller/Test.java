package com.bengyun.webservice.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
	
	@RequestMapping("/hello")
	public String Hello(String name) {
		return "Hello " + name;
	}
}
