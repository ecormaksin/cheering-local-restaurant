package com.cheeringlocalrestaurant.presentation.controller.utils;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.StandardTextTagProcessor;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

public class TextNewLineConverterProcessorDialect extends AbstractProcessorDialect implements IProcessorDialect {

	private static final String DIALECT_NAME = "TextNewLineConverterProcessorDialect";
	private static final String DIALECT_PREFIX = "thex";

	public TextNewLineConverterProcessorDialect() {
		this(DIALECT_PREFIX);
	}
	
	public TextNewLineConverterProcessorDialect(String dialectPrefix) {
		super(DIALECT_NAME, dialectPrefix, StandardTextTagProcessor.PRECEDENCE - 1);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new TextNewLineConvertProcessor(TemplateMode.HTML, dialectPrefix));
		processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
		return processors;
	}
}
