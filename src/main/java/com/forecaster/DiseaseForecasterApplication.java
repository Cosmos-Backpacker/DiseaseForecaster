package com.forecaster;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.forecaster.mapper")
@SpringBootApplication
@EnableScheduling
public class DiseaseForecasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiseaseForecasterApplication.class, args);
	}

}
