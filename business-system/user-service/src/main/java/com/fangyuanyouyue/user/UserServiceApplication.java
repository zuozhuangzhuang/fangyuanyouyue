package com.fangyuanyouyue.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@RefreshScope
@EnableFeignClients
@Configuration
public class UserServiceApplication {
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
		SpringApplication.run(UserServiceApplication.class, args);
	}

	/**
	 * 文件上传配置
	 *
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//  单个数据大小
		factory.setMaxFileSize("1024MB"); // KB,MB
		/// 总上传数据大小
		factory.setMaxRequestSize("10240MB");
		return factory.createMultipartConfig();
	}
}
