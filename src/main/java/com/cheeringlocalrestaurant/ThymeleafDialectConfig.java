package com.cheeringlocalrestaurant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheeringlocalrestaurant.presentation.view.TextNewLineConverterProcessorDialect;

@Configuration
public class ThymeleafDialectConfig {

	@Bean
	public TextNewLineConverterProcessorDialect textNewLineConverterProcessorDialect() {
		return new TextNewLineConverterProcessorDialect();
	}
}
