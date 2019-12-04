/*
 * SetterController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SetterService;
import services.RecordService;
import controllers.AbstractController;
import domain.Setter;
import domain.Record;

@Controller
@RequestMapping("/setter")
public class SetterController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SetterService		setterService;
	@Autowired
	private RecordService		recordService;

	private Record 		recordSelected;


	// Constructors -----------------------------------------------------------

	public SetterController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listByRecord", method = RequestMethod.GET)
	public ModelAndView listByRecord(@RequestParam final int recordId) {
		ModelAndView result;
		Collection<Setter> setters;
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		setters = this.setterService.findByRecordAndAvailable(recordId);
		this.recordSelected = this.recordService.findOne(recordId);
		result = new ModelAndView("setter/listByRecord");
		result.addObject("requestURI", "setter/listByRecord.do");
		result.addObject("setters", setters);
		result.addObject("language", language);
		result.addObject("recordSelected", recordSelected);
		return result;
	}
	
	// Display
	
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int setterId) {
			ModelAndView result;
			Setter setter;
			setter = this.setterService.findOne(setterId);
			Assert.isTrue(this.setterService.findByPrincipal().contains(setter) ||  setter.isFinalMode());
			Locale locale = LocaleContextHolder.getLocale();
			String language = locale.getLanguage();
			setter = this.setterService.findOne(setterId);
			result = new ModelAndView("setter/display");
			result.addObject("requestURI", "setter/display.do");
			result.addObject("setter", setter);
			result.addObject("language", language);
			return result;

		}


	// Ancillary methods ------------------------------------------------------


	
	

}
