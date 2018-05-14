package com.fangyuanyouyue.client1;

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
public class Client1Application {
	private static final Logger LOG = Logger.getLogger(Client1Application.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(Client1Application.class, args);
	}

	@Value("${server.port}")
	String port;
	@RequestMapping("/hi")
	public String hi(@RequestParam String name) {
		LOG.log(Level.INFO, "calling trace service-hi  ");
		return "hi "+name+",i am from port:" +port;
	}

	@Value("${foo}")
	String foo;
	@RequestMapping("/configClient")
	public String configClient(){
		return foo;
	}
}

