package com.fangyuanyouyue.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
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
	 * **                        佛祖保佑      启动正常                        **
	 * ************************************************************************
	 *
	 */

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
