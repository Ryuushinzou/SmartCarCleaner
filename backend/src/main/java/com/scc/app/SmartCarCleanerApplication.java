package com.scc.app;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@SpringBootApplication(scanBasePackages = {"com.scc.app"})
@EnableScheduling
@EnableTransactionManagement
@EnableJSONDoc
public class SmartCarCleanerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCarCleanerApplication.class, args);
	}

}
