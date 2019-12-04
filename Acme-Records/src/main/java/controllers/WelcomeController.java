/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdminConfigService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	@Autowired
	private AdminConfigService	adminConfigService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		final Locale locale = LocaleContextHolder.getLocale();
		final String language = locale.getLanguage();
		String welcomeMessage = "";
		final String banner = this.adminConfigService.findConf().getBanner();
		
		if (language == "es")
			welcomeMessage = this.adminConfigService.findConf().getWelcomeMessageSpanish();
		else if (language == "en")
			welcomeMessage = this.adminConfigService.findConf().getWelcomeMessageEnglish();

		result = new ModelAndView("welcome/index");
		result.addObject("banner", banner);
		result.addObject("welcomeMessage", welcomeMessage);

		return result;
	}
}
