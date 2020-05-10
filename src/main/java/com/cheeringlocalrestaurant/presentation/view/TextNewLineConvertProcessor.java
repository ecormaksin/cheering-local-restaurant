package com.cheeringlocalrestaurant.presentation.view;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.processor.AbstractStandardExpressionAttributeTagProcessor;
import org.thymeleaf.standard.processor.StandardTextTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.LazyEscapingCharSequence;
import org.unbescape.html.HtmlEscape;
import org.unbescape.xml.XmlEscape;

public class TextNewLineConvertProcessor extends AbstractStandardExpressionAttributeTagProcessor {

	public static final String ATTR_NAME = "textbr";
	
	protected TextNewLineConvertProcessor(final TemplateMode templateMode, final String dialectPrefix) {
		super(templateMode, dialectPrefix, ATTR_NAME, StandardTextTagProcessor.PRECEDENCE - 1, true);
	}

	@Override
	protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final AttributeName attributeName,
			final String attributeValue, final Object expressionResult, final IElementTagStructureHandler structureHandler) {
		
		final String input = (expressionResult == null ? "" : expressionResult.toString());
		String replaced = input;

		final TemplateMode templateMode = getTemplateMode();

		CharSequence text;

		if (templateMode != TemplateMode.JAVASCRIPT && templateMode != TemplateMode.CSS) {

			if (templateMode == TemplateMode.RAW) {
				// RAW -> just output

				text = replaced;

			} else {

				if (replaced.length() > 100) {
					// Might be a large text -> Lazy escaping on the output Writer
					text = new LazyEscapingCharSequence(context.getConfiguration(), templateMode, replaced);
				} else {
					// Not large -> better use a bit more of memory, but be faster
					text = produceEscapedOutput(templateMode, replaced);
				}

			}

		} else {
			// JavaScript and CSS serializers always work directly on the output Writer, no need to store the entire
			// serialized contents in memory (unless the Writer itself wants to do so).

			text = new LazyEscapingCharSequence(context.getConfiguration(), templateMode, expressionResult);

		}

		if (templateMode.isMarkup()) {
			replaced = text.toString();
			replaced = replaced.replaceAll("\r\n|\r|\n", "<br/>");
			text = replaced;
		}

		// Report the result to the engine, whichever the type of process we have applied
		structureHandler.setBody(text, false);
	}

	private static String produceEscapedOutput(final TemplateMode templateMode, final String input) {

		switch (templateMode) {

			case TEXT:
				// fall-through
			case HTML:
				return HtmlEscape.escapeHtml4Xml(input);
			case XML:
				// Note we are outputting a body content here, so it is important that we use the version
				// of XML escaping meant for content, not attributes (slight differences)
				return XmlEscape.escapeXml10(input);
			default:
				throw new TemplateProcessingException(
						"Unrecognized template mode " + templateMode + ". Cannot produce escaped output for " +
						"this template mode.");
		}

	}
}
