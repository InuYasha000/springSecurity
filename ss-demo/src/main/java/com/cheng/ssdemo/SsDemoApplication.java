package com.cheng.ssdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.cheng.brower", "com.cheng.ssdemo","com.cheng.core"})
public class SsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsDemoApplication.class, args);
		System.out.println("-------------");
	}

}
