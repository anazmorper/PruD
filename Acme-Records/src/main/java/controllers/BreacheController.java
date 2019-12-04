/*
 * BreacheAdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BreacheService;
import controllers.AbstractController;
import domain.Breache;

@Controller
@RequestMapping("/breache")
public class BreacheController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BreacheService		breacheService;


	// Constructors -----------------------------------------------------------

	public BreacheController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Breache> breaches;

		breaches = this.breacheService.findAll();
		result = new ModelAndView("breache/list");
		result.addObject("requestURI", "breache/list.do");
		result.addObject("breaches", breaches);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Breache breache;

		breache = this.breacheService.create();
		result = this.createEditModelAndView(breache);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Breache breache, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(breache);
		else
			try {
				this.breacheService.save(breache);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(breache, "breache.commit.error");
			}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int breacheId) {
		ModelAndView result;
		Breache breache;

		breache = this.breacheService.findOne(breacheId);
		Assert.notNull(breache);
		result = this.createEditModelAndView(breache);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Breache breache, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(breache);
		else
			try {
				this.breacheService.save(breache);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(breache, "breache.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Breache breache) {
		ModelAndView result;

		result = this.createEditModelAndView(breache, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Breache breache, final String message) {
		ModelAndView result;
		result = new ModelAndView("breache/edit");
		result.addObject("breache", breache);
		result.addObject("message", message);

		return result;
	}

}
