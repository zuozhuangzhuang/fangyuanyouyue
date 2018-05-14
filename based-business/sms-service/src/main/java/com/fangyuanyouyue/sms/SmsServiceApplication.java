package com.fangyuanyouyue.sms;

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
public class SmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsServiceApplication.class, args);
	}

	private static final Logger LOG = Logger.getLogger(SmsServiceApplication.class.getName());
	@Value("${server.port}")
	String port;
	@RequestMapping("/sms")
	public String sms(@RequestParam String name) {
		LOG.log(Level.INFO, "calling trace based-business  ");
		return "sms "+name+",i am from port:" +port;
	}
}
