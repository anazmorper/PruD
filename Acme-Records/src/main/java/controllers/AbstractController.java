/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountService;
import services.AdminConfigService;

@Controller
public class AbstractController {

	@Autowired
	private UserAccountService	userAccountService;
	
	@Autowired
	private AdminConfigService	adminConfigService;


	@ModelAttribute(value = "banner")
	public String banner() {
		String result;

		result = this.adminConfigService.getSystemBanner();
		return result;
	}

	@ModelAttribute(value = "countryCodeAttr")
	public String countryCodeAttr() {
		String result;

		result = this.adminConfigService.getDefaultCountryCode();

		return result;
	}


	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	public UserAccount hashSavePassword(final UserAccount userAccount) {
		UserAccount result;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));

		result = this.userAccountService.save(userAccount);

		return result;
	}

}