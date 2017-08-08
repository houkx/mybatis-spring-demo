package com.mq.demo.springmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 */
@ImportResource("beans.xml")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class App {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
