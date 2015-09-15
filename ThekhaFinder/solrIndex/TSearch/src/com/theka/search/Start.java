package com.theka.search;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

	public static void main(String[] args) {

		@SuppressWarnings({ "resource", "unused" })
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
	}

}
