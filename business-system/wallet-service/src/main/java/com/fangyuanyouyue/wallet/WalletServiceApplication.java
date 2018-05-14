package com.fangyuanyouyue.wallet;

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
public class WalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}

	private static final Logger LOG = Logger.getLogger(WalletServiceApplication.class.getName());
	@Value("${server.port}")
	String port;
	@RequestMapping("/wallet")
	public String wallet(@RequestParam String name) {
		LOG.log(Level.INFO, "calling trace wallet-service  ");
		return "wallet "+name+",i am from port:" +port;
	}
}
