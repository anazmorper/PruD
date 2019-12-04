/*
 * SetteradministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/administrator/setter")
public class SetterAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SetterService		setterService;
	@Autowired
	private RecordService		recordService;

	private Record 		recordSelected;
	
	private boolean canEdit;
	private boolean canCreate;


	// Constructors -----------------------------------------------------------

	public SetterAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Setter> setters;
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		setters = this.setterService.findByPrincipal();
		result = new ModelAndView("setter/list");
		result.addObject("requestURI", "administrator/setter/list.do");
		result.addObject("setters", setters);
		result.addObject("language", language);
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int recordId) {
		ModelAndView result;
		Setter setter;
		this.recordSelected = this.recordService.findOne(recordId);
		
		canCreate = true;
		
		setter = this.setterService.create();
		result = this.createEditModelAndView2(setter);
		result.addObject("canCreate", canCreate);
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Setter setter, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()){
			result = this.createEditModelAndView2(setter);
			
	}else{
			try {
				this.setterService.saveCreate(setter, recordSelected);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView2(setter, "setter.commit.error");
			}}
		result.addObject("canCreate", canCreate);
		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int setterId) {
		ModelAndView result;
		Setter setter;
		Collection<Setter> settersPrincipal = new ArrayList<Setter>();
		canEdit = false;
		
		settersPrincipal = setterService.findByPrincipal();

		setter = this.setterService.findOne(setterId);
		Assert.notNull(setter);
		Assert.isTrue(settersPrincipal.contains(setter));
		Assert.isTrue(!setter.isFinalMode());
		if (settersPrincipal.contains(setter)) {
			canEdit = true;
		}
		
		
		result = this.createEditModelAndView(setter);
		result.addObject("canEdit", canEdit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Setter setter, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()){
			result = this.createEditModelAndView(setter);
	}else{
			try {
				this.setterService.save(setter);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(setter, "setter.commit.error");
			}}
		result.addObject("canEdit", canEdit);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int setterId) {
		ModelAndView result;
		final Setter setter;

		setter = this.setterService.findOne(setterId);
		Assert.notNull(setter);
		try {
		
			this.setterService.delete(setter);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listWithMessage("setter.commit.error");
		}

		return result;
	}
	
	// Display
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int setterId) {
		ModelAndView result;
		Setter setter;
		setter = this.setterService.findOne(setterId);
		Assert.isTrue((this.setterService.findByPrincipal().contains(setter)) || (!this.setterService.findByPrincipal().contains(setter) && setter.isFinalMode()));
		result = new ModelAndView("setter/display");
		result.addObject("requestURI", "/administrator/setter/display.do");
		result.addObject("setter", setter);
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Setter setter) {
		ModelAndView result;

		result = this.createEditModelAndView(setter, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Setter setter, final String message) {
		ModelAndView result;
		result = new ModelAndView("setter/edit");
		result.addObject("setter", setter);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView2(final Setter setter) {
		ModelAndView result;

		result = this.createEditModelAndView2(setter, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Setter setter, final String message) {
		ModelAndView result;


		result = new ModelAndView("setter/create");
		result.addObject("setter", setter);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView listWithMessage(final String message) {
		final ModelAndView result;
		Collection<Setter> setters;
		setters = this.setterService.findByPrincipal();
		result = new ModelAndView("setter/list");
		result.addObject("setters", setters);
		result.addObject("requestURI", "/administrator/setter/list.do");
		result.addObject("message", message);
		return result;

	}

}
