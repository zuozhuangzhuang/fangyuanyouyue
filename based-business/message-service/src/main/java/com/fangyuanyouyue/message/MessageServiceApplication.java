package com.fangyuanyouyue.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class MessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}

	private static final Logger LOG = Logger.getLogger(MessageServiceApplication.class.getName());
	@Value("${server.port}")
	String port;
	@RequestMapping("/message")
	public String message(@RequestParam String name) {
		LOG.log(Level.INFO, "calling trace based-business  ");
		return "message "+name+",i am from port:" +port;
	}
}
