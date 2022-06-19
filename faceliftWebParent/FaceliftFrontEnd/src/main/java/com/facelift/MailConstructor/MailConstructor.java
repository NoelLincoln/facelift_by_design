package com.facelift.MailConstructor;

import com.facelift.common.entity.Contact;
//import com.eCommerce.domain.Order;
import com.facelift.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;


@Component
public class MailConstructor {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private Environment env;

//	@Autowired
//	private JavaMailSender emailSender;


//	@Autowired
//	private SimpleMailMessage template;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

//	@Autowired
//	private FreeMarkerConfigurer freemarkerConfigurer;

	@Value("classpath:/mail-logo.png")
	private Resource resourceFile;

//




	public SimpleMailMessage contactEmail(
            String contextPath, Locale locale, Contact contact
	) {

//		String url = contextPath + "/newUser?token=";
		String message = contact.getMessage();
//		String mail = contact.getEmail();
		SimpleMailMessage email = new SimpleMailMessage();
		String adminmail ="info@facelift.co.ke";
		email.setTo(adminmail);
		email.setSubject(contact.getSubject());
		email.setText(message);
		email.setFrom(contact.getEmail());
		return email;

	}


	
	
}
