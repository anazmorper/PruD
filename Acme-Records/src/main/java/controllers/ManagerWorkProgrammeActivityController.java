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

import java.util.ArrayList;
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
import services.AdminConfigService;
import services.ManagerService;
import services.WorkProgrammeService;
import services.ActivityService;
import domain.Activity;
import domain.AdminConfig;
import domain.Manager;
import domain.WorkProgramme;

@Controller
@RequestMapping("/workProgramme/activity/manager")
public class ManagerWorkProgrammeActivityController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private ManagerService managerService;

	@Autowired
	private WorkProgrammeService workProgrammeService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private AdminConfigService		adminConfigService;
	
	private WorkProgramme workProgramme;

	// Constructors -----------------------------------------------------------

	public ManagerWorkProgrammeActivityController() {
		super();
	}

	// Create ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Activity activity;
		activity = this.activityService.create();

		result = this.createEditModelAndView(activity);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/editcreate", method = RequestMethod.GET)
	public ModelAndView editcreate(@RequestParam final int activityId) {
		ModelAndView result;
		Activity activity;
		String requestURI;
		this.managerService.checkPrincipal();
		activity = this.activityService.findOne(activityId);
		requestURI = "/workProgramme/activity/manager/editcreate.do";
		result = this.createEditModelAndView(activity);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/editcreate", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Activity activity,
			final BindingResult binding) {
		ModelAndView result;
		boolean errorDate = false;
		
		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors())
			result = this.createEditModelAndView(activity);
		else
			try {				
				
				if (!activity.getStartDate().after(new Date()) ||
						!activity.getEndDate().after(new Date()) ||
						!activity.getStartDate().after(workProgramme.getStartDate()) ||
						!activity.getStartDate().before(workProgramme.getEndDate()) ||
						!activity.getEndDate().after(workProgramme.getStartDate()) ||
						!activity.getEndDate().before(workProgramme.getEndDate()) ||
						!activity.getEndDate().after(activity.getStartDate())){
					errorDate = true;
				}
				
				this.activityService.save(activity, this.workProgramme);
				result = new ModelAndView("redirect:/workProgramme/manager/listMyWorkProgrammes.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				if (errorDate){
					result = this.createEditModelAndView(activity,
							"activity.commit.error.date");
				}else {
					result = this.createEditModelAndView(activity,
						"activity.commit.error");
				}
			}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int activityId) {
		ModelAndView result;
		Activity activity;
		String requestURI;
		this.managerService.checkPrincipal();
		activity = this.activityService.findOne(activityId);
		Assert.isTrue(this.activityService.activitiesByManager(this.managerService.findOneByPrincipal()).contains(activity)
				&& this.workProgrammeService.WorkProgrammeByActivity(activity).getEndDate().after(new Date()));
		requestURI = "/workProgramme/activity/manager/edit.do";
		result = this.createEditModelAndView2(activity);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Activity activity,
			final BindingResult binding) {
		ModelAndView result;
		boolean errorDate = false;

		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors())
			result = this.createEditModelAndView2(activity);
		else
			try {
				
				if (!activity.getStartDate().after(new Date()) ||
						!activity.getEndDate().after(new Date()) ||
						!activity.getStartDate().after(workProgramme.getStartDate()) ||
						!activity.getStartDate().before(workProgramme.getEndDate()) ||
						!activity.getEndDate().after(workProgramme.getStartDate()) ||
						!activity.getEndDate().before(workProgramme.getEndDate()) ||
						!activity.getEndDate().after(activity.getStartDate())){
					errorDate = true;
				}
				
				this.activityService.save(activity, this.workProgramme);
				result = new ModelAndView("redirect:/workProgramme/manager/listMyWorkProgrammes.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				if (errorDate){
					result = this.createEditModelAndView2(activity,
							"activity.commit.error.date");
				}else {
					result = this.createEditModelAndView2(activity,
						"activity.commit.error");
				}
			}
		return result;
	}

	// Listing ----------------------------------------------------------------

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam final int workProgrammeId) {
//		ModelAndView result;
//		Collection<Activity> activities;
//		WorkProgramme workProgramme = this.workProgrammeService.findOne(workProgrammeId);
//		activities = workProgramme.getActivities();
//		result = new ModelAndView("activity/list");
//		result.addObject("activities", activities);
//
//		result.addObject("requestURI", "/workProgramme/activity/manager/list.do");
//		return result;
//	}
	
	@RequestMapping(value = "/listbyworkprogramme", method = RequestMethod.GET)
	public ModelAndView listbyworkprogramme(@RequestParam Integer workProgrammeId) {
		ModelAndView result;
		Collection<Activity> r;
		WorkProgramme workProgramme;
		workProgramme = workProgrammeService.findOne(workProgrammeId);
		this.workProgramme = workProgramme;
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
		r = workProgramme.getActivities();
		result = new ModelAndView("activity/list");
		result.addObject("requestURI", "/workProgramme/activity/manager/listbyrecordaut.do");
		result.addObject("activities", r);
		result.addObject("thiswk", workProgramme);
		result.addObject("mylist", this.activityService.activitiesByManager(this.managerService.findOneByPrincipal()));
	

		return result;
	}

	// Delete profile

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int activityId) {
		ModelAndView result;

		final Activity activity;

		this.managerService.checkPrincipal();
		activity = this.activityService.findOne(activityId);
		this.activityService.delete(activity, workProgramme);

		result = new ModelAndView("redirect:/workProgramme/manager/listMyWorkProgrammes.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int activityId) {
		ModelAndView result;
		Activity activity;
		this.managerService.checkPrincipal();
		activity = this.activityService.findOne(activityId);
		Boolean canDelete = false;
		Manager manager = this.managerService.findByUserAccount(LoginService.getPrincipal());
		Collection<Activity> activitiesByManager = this.activityService.activitiesByManager(manager);
		Assert.isTrue(activitiesByManager.contains(activity));
		if(activitiesByManager.contains(activity))
			canDelete = true;
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();

		result = new ModelAndView("activity/display");
		result.addObject("requestURI", "/workProgramme/activity/manager/display.do");
		result.addObject("activity", activity);
		result.addObject("language", language);
		result.addObject("canDelete", canDelete);

		return result;

	}

	// Ancilliary methods
	// -----------------------------------------------------------

	private ModelAndView createEditModelAndView(
			final Activity activity) {
		ModelAndView result;

		result = this.createEditModelAndView(activity, null);

		return result;
	}

	private ModelAndView createEditModelAndView(
		final Activity activity, final String message) {
		ModelAndView result;
		String requestURI;
		AdminConfig adminConfig = this.adminConfigService.findOne();
		
		Collection<String> status = new ArrayList<String>(adminConfig.getStatus());

		result = new ModelAndView("activity/editcreate");
		requestURI = "/workProgramme/activity/manager/editcreate.do";
		result.addObject("activity", activity);
		result.addObject("status", status);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
	
	private ModelAndView createEditModelAndView2(
			final Activity activity) {
		ModelAndView result;

		result = this.createEditModelAndView2(activity, null);

		return result;
	}

	private ModelAndView createEditModelAndView2(
		final Activity activity, final String message) {
		ModelAndView result;
		String requestURI;
		AdminConfig adminConfig = this.adminConfigService.findOne();
		
		Collection<String> status = new ArrayList<String>(adminConfig.getStatus());

		result = new ModelAndView("activity/edit");
		requestURI = "/workProgramme/activity/manager/edit.do";
		result.addObject("activity", activity);
		result.addObject("status", status);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
