package com.cheeringlocalrestaurant.infra.mail;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class FreeMarkerMailSender {

    @Autowired
    CustomMailService customMailService;

    @Autowired
    private Configuration freemarkerConfig;

    @PostConstruct
    void postConstruct() {
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/mail_templates");
    }

    // @formatter:off
    public CustomMailBody getProcessedBody(String templateName, Object model) 
            throws IOException, TemplateException {
    // @formatter:on

        Template template = freemarkerConfig.getTemplate(templateName + ".ftl");
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        
        return new CustomMailBody(body);
    }
    
    // @formatter:off
    public void send(MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            String templateName,
            Object model) throws IOException, TemplateException {
    // @formatter:on
        send(customMailService.getDefaultMailSender(), mailAddress, customMailSubject, templateName, model);
    }
    // @formatter:off
    public void send(CustomMailSender mailSender, 
            MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            String templateName,
            Object model) throws IOException, TemplateException {
    // @formatter:on

        CustomMailBody customMailBody = getProcessedBody(templateName, model);

        // @formatter:off
        customMailService.send(mailSender, 
                new MailAddress(mailAddress.getValue()), 
                new CustomMailSubject(customMailSubject.getValue()), 
                customMailBody);
        // @formatter:on
    }

    public Optional<MimeMessage[]> getMimeMessages() {
        return customMailService.getMimeMessages();
    }
}
