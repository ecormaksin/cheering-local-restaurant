package com.cheeringlocalrestaurant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheeringlocalrestaurant.presentation.controller.utils.TextNewLineConverterProcessorDialect;

@Configuration
public class ThymeleafDialectConfig {

	@Bean
	public TextNewLineConverterProcessorDialect textNewLineConverterProcessorDialect() {
		return new TextNewLineConverterProcessorDialect();
	}
}
