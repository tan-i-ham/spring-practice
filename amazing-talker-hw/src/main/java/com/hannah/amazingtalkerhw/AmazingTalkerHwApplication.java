package com.hannah.amazingtalkerhw;

import com.hannah.amazingtalkerhw.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AmazingTalkerHwApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazingTalkerHwApplication.class, args);
	}

}
