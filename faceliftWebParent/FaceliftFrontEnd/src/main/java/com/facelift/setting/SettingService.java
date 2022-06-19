package com.facelift.setting;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.facelift.common.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.facelift.common.entity.Currency;
import com.facelift.common.entity.setting.Setting;
import com.facelift.common.entity.setting.SettingCategory;

@Service
public class SettingService {
	@Autowired private SettingRepository settingRepo;
	@Autowired private CurrencyRepository currencyRepo;

	public List<Setting> getGeneralSettings() {
		return settingRepo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
	}
	
	public EmailSettingBag getEmailSettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.MAIL_SERVER);
		settings.addAll(settingRepo.findByCategory(SettingCategory.MAIL_TEMPLATES));
		
		return new EmailSettingBag(settings);
	}
	
	public CurrencySettingBag getCurrencySettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.CURRENCY);
		return new CurrencySettingBag(settings);
	}
	
	public PaymentSettingBag getPaymentSettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.PAYMENT);
		return new PaymentSettingBag(settings);
	}
	
	public String getCurrencyCode() {
		Setting setting = settingRepo.findByKey("CURRENCY_ID");
		Integer currencyId = Integer.parseInt(setting.getValue());
		Currency currency = currencyRepo.findById(currencyId).get();
		
		return currency.getCode();
	}

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
