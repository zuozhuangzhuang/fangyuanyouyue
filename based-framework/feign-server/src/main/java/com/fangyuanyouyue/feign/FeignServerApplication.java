package com.fangyuanyouyue.feign;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
public class FeignServerApplication {
	/**
	 * ************************************************************************
	 * **                              _oo0oo_                               **
	 * **                             o8888888o                              **
	 * **                             88" . "88                              **
	 * **                             (| -_- |)                              **
	 * **                             0\  =  /0                              **
	 * **                           ___/'---'\___                            **
	 * **                        .' \\\|     |// '.                          **
	 * **                       / \\\|||  :  |||// \\                        **
	 * **                      / _ ||||| -:- |||||- \\                       **
	 * **                      | |  \\\\  -  /// |   |                       **
	 * **                      | \_|  ''\---/''  |_/ |                       **
	 * **                      \  .-\__  '-'  __/-.  /                       **
	 * **                    ___'. .'  /--.--\  '. .'___                     **
	 * **                 ."" '<  '.___\_<|>_/___.' >'  "".                  **
	 * **                | | : '-  \'.;'\ _ /';.'/ - ' : | |                 **
	 * **                \  \ '_.   \_ __\ /__ _/   .-' /  /                 **
	 * **            ====='-.____'.___ \_____/___.-'____.-'=====             **
	 * **                              '=---='                               **
	 * ************************************************************************
	 * **                        佛祖保佑      启动正常                      **
	 * ************************************************************************
	 *
	 */

	public static void main(String[] args) {
		SpringApplication.run(FeignServerApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean getServlet(){
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/hystrix.stream");
		registrationBean.setName("HystrixMetricsStreamServlet");
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}
}
