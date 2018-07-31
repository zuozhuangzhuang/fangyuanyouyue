package com.fangyuanyouyue.forum;

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

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@RefreshScope
@EnableFeignClients
@Configuration
public class ForumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}


//	private static final Logger LOG = Logger.getLogger(ForumServiceApplication.class.getName());
//	@Value("${server.port}")
//	String port;
//	@RequestMapping("/forum")
//	public String forum(@RequestParam String name) {
//		LOG.log(Level.INFO, "calling trace business-system  ");
//		return "forum "+name+",i am from port:" +port;
//	}

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
