/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ManagerService;
import services.WorkProgrammeService;
import domain.Manager;
import domain.WorkProgramme;

@Controller
@RequestMapping("/manager/workProgramme")
public class ManagerWorkProgrammeController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private ManagerService managerService;

	@Autowired
	private WorkProgrammeService workProgrammeService;

	// Constructors -----------------------------------------------------------

	public ManagerWorkProgrammeController() {
		super();
	}

	// Create ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WorkProgramme workProgramme;

		workProgramme = this.workProgrammeService.create();

		result = this.createEditModelAndView(workProgramme);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/editcreate", method = RequestMethod.GET)
	public ModelAndView editcreate(@RequestParam final int workProgrammeId) {
		ModelAndView result;
		WorkProgramme workProgramme;
		String requestURI;
		this.managerService.checkPrincipal();
		workProgramme = this.workProgrammeService.findOne(workProgrammeId);
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme) && workProgramme.getEndDate().after(new Date()));
		requestURI = "workProgramme/editcreate.do";
		result = this.createEditModelAndView(workProgramme);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/editcreate", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final WorkProgramme workProgramme,
			final BindingResult binding) {
		ModelAndView result;
		boolean errorDate = false;

		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors())
			result = this.createEditModelAndView(workProgramme);
		else
			try {
				
				if (!workProgramme.getStartDate().after(new Date()) ||
						!workProgramme.getEndDate().after(new Date()) ||
						!workProgramme.getEndDate().after(workProgramme.getStartDate())){
					errorDate = true;
				}
				
				this.workProgrammeService.save(workProgramme);
				result = new ModelAndView("redirect:/manager/workProgramme/listMyWorkProgrammes.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				if (errorDate){
					result = this.createEditModelAndView(workProgramme,
							"workProgramme.commit.error.date");
				}else {
					result = this.createEditModelAndView(workProgramme,
						"workProgramme.commit.error");
				}
			}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int workProgrammeId) {
		ModelAndView result;
		WorkProgramme workProgramme;
		String requestURI;
		this.managerService.checkPrincipal();
		workProgramme = this.workProgrammeService.findOne(workProgrammeId);
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme) && workProgramme.getEndDate().after(new Date()));
		requestURI = "workProgramme/edit.do";
		result = this.createEditModelAndView2(workProgramme);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final WorkProgramme workProgramme,
			final BindingResult binding) {
		ModelAndView result;
		boolean errorDate = false;

		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors())
			result = this.createEditModelAndView2(workProgramme);
		else
			try {
				if (!workProgramme.getStartDate().after(new Date()) ||
						!workProgramme.getEndDate().after(new Date()) ||
						!workProgramme.getEndDate().after(workProgramme.getStartDate())){
					errorDate = true;
				}
				
				this.workProgrammeService.save(workProgramme);
				result = new ModelAndView("redirect:/manager/workProgramme/listMyWorkProgrammes.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				if (errorDate){
					result = this.createEditModelAndView2(workProgramme,
							"workProgramme.commit.error.date");
				}else {
					result = this.createEditModelAndView2(workProgramme,
						"workProgramme.commit.error");
				}
			}
		return result;
	}

	// Listing ----------------------------------------------------------------

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list() {
//		ModelAndView result;
//		Collection<WorkProgramme> workProgrammes;
//		Manager manager = this.managerService.findByUserAccount(LoginService.getPrincipal());
//		workProgrammes = this.workProgrammeService.findAll();
//		result = new ModelAndView("workProgramme/list");
//		
//		result.addObject("workProgrammes", workProgrammes);
//		result.addObject("mylist", manager.getWorkProgrammes());
//
//		result.addObject("requestURI", "/manager/workProgramme/list.do");
//		return result;
//	}
	
	@RequestMapping(value = "/listMyWorkProgrammes", method = RequestMethod.GET)
	public ModelAndView listMyWorkProgrammes() {
		ModelAndView result;
		Collection<WorkProgramme> workProgrammes;
		workProgrammes = this.managerService.findOneByPrincipal().getWorkProgrammes();
		result = new ModelAndView("workProgramme/listMyWorkProgrammes");
		result.addObject("workProgrammes", workProgrammes);	

		result.addObject("requestURI", "/manager/workProgramme/listMyWorkProgrammes.do");
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int workProgrammeId) {
		ModelAndView result;

		final WorkProgramme workProgramme;

		this.managerService.checkPrincipal();
		workProgramme = this.workProgrammeService.findOne(workProgrammeId);
		this.workProgrammeService.delete(workProgramme);
		result = new ModelAndView("redirect:/manager/workProgramme/listMyWorkProgrammes.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int workProgrammeId) {
		ModelAndView result;
		WorkProgramme workProgramme;
		this.managerService.checkPrincipal();
		workProgramme = this.workProgrammeService.findOne(workProgrammeId);
		Boolean canDelete = false;
		Manager manager = this.managerService.findByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(manager.getWorkProgrammes().contains(workProgramme));
		if(manager.getWorkProgrammes().contains(workProgramme))
			canDelete = true;
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();

		result = new ModelAndView("workProgramme/display");
		result.addObject("requestURI", "/manager/workProgramme/display.do");
		result.addObject("workProgramme", workProgramme);
		result.addObject("language", language);
		result.addObject("canDelete", canDelete);
		return result;

	}

	// Ancilliary methods
	// -----------------------------------------------------------

	private ModelAndView createEditModelAndView(
			final WorkProgramme workProgramme) {
		ModelAndView result;

		result = this.createEditModelAndView(workProgramme, null);

		return result;
	}

	private ModelAndView createEditModelAndView(
			final WorkProgramme workProgramme, final String message) {
		ModelAndView result;
		String requestURI;

		result = new ModelAndView("workProgramme/editcreate");
		requestURI = "/manager/workProgramme/editcreate.do";
		result.addObject("workProgramme", workProgramme);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
	
	private ModelAndView createEditModelAndView2(
			final WorkProgramme workProgramme) {
		ModelAndView result;

		result = this.createEditModelAndView2(workProgramme, null);

		return result;
	}

	private ModelAndView createEditModelAndView2(
			final WorkProgramme workProgramme, final String message) {
		ModelAndView result;
		String requestURI;

		result = new ModelAndView("workProgramme/edit");
		requestURI = "/manager/workProgramme/edit.do";
		result.addObject("workProgramme", workProgramme);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
