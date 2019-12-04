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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdminConfigService;
import services.ArtistService;
import services.ManagerService;
import services.RecordService;
import services.WorkProgrammeService;
import domain.AdminConfig;
import domain.Artist;
import domain.Manager;
import domain.Record;
import domain.WorkProgramme;

@Controller
@RequestMapping("/workProgramme/record/manager")
public class ManagerWorkProgrammeRecordController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private ManagerService managerService;

	@Autowired
	private WorkProgrammeService workProgrammeService;
	
	@Autowired
	private RecordService recordService;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private AdminConfigService	adminConfigService;
	
	private WorkProgramme workProgramme;

	// Constructors -----------------------------------------------------------

	public ManagerWorkProgrammeRecordController() {
		super();
	}

	// Create ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Record record;
		record = this.recordService.createWorkProgramme();
		

		result = this.createEditModelAndView(record);

		return result;
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/editcreate", method = RequestMethod.GET)
	public ModelAndView editcreate(@RequestParam final int recordId) {
		ModelAndView result;
		this.recordService.updateFinalModeRecords();
		Record record;
		String requestURI;
		this.managerService.checkPrincipal();
		record = this.recordService.findOne(recordId);
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme) && this.workProgramme.getEndDate().after(new Date()));
		requestURI = "record/editcreate.do";
		result = this.createEditModelAndView(record);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/editcreate", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Record record,
			final BindingResult binding) {
		ModelAndView result;
		this.recordService.updateFinalModeRecords();


		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors()){
			FieldError errorAttachmentUrl = binding.getFieldError("attachments[].url");
		if (null != errorAttachmentUrl){
			result = this.createEditModelAndView(record, "record.commit.error.attachment");
		} else {
			result = this.createEditModelAndView(record);
		}
		}else
			try {
				this.recordService.saveWorkProgramme(record, this.workProgramme);
				result = new ModelAndView("redirect:/workProgramme/manager/listMyWorkProgrammes.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(record,
						"record.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int recordId) {
		ModelAndView result;
		this.recordService.updateFinalModeRecords();
		Record record;
		String requestURI;
		this.managerService.checkPrincipal();
		record = this.recordService.findOne(recordId);
		Assert.isTrue(this.recordService.recordsByManager(this.managerService.findOneByPrincipal()).contains(record));
		Assert.isTrue(!record.isFinalMode());
		requestURI = "record/edit.do";
		result = this.createEditModelAndView2(record);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Record record,
			final BindingResult binding) {
		ModelAndView result;

		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors()){
			FieldError errorAttachmentUrl = binding.getFieldError("attachments[].url");
			if (null != errorAttachmentUrl){
				result = this.createEditModelAndView2(record, "record.commit.error.attachment");
			} else {
				result = this.createEditModelAndView2(record);
			}
		}else
			try {
				this.recordService.saveWorkProgramme(record, this.workProgramme);
				result = new ModelAndView("redirect:/workProgramme/manager/listMyWorkProgrammes.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView2(record,
						"record.commit.error");
			}
		return result;
	}

	// Listing ----------------------------------------------------------------

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam final int workProgrammeId) {
//		ModelAndView result;
//		Collection<Record> records;
//		WorkProgramme workProgramme = this.workProgrammeService.findOne(workProgrammeId);
//		this.workProgramme = workProgramme;
//		records = workProgramme.getRecords();
//		result = new ModelAndView("record/list");
//		result.addObject("records", records);
//
//		result.addObject("requestURI", "/workProgramme/record/manager/list.do");
//		return result;
//	}
	
	@RequestMapping(value = "/listbyworkprogramme", method = RequestMethod.GET)
	public ModelAndView listbyworkprogramme(@RequestParam Integer workProgrammeId) {
		ModelAndView result;
		Collection<Record> r;
		WorkProgramme workProgramme;
		workProgramme = workProgrammeService.findOne(workProgrammeId);
		Boolean puedeCrear = false;
		this.workProgramme = workProgramme;
		this.recordService.updateFinalModeRecords();
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
		if(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme) 
				&& workProgramme.getEndDate().after(new Date())){
			puedeCrear = true;
		}
		r = workProgramme.getRecords();
		result = new ModelAndView("record/listByWorkProgramme");
		result.addObject("requestURI", "/workProgramme/record/manager/listbyworkprogramme.do");
		result.addObject("records", r);
		result.addObject("endDateWP", workProgramme.getEndDate());
		result.addObject("mylist", this.recordService.recordsByManager(this.managerService.findOneByPrincipal()));
		result.addObject("puedeCrear", puedeCrear);
	

		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int recordId) {
		ModelAndView result;

		final Record record;
		this.recordService.updateFinalModeRecords();

		this.managerService.checkPrincipal();
		record = this.recordService.findOne(recordId);
		this.recordService.deleteWorkProgramme(record, this.workProgramme);

		result = new ModelAndView("redirect:/workProgramme/manager/listMyWorkProgrammes.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int recordId) {
		ModelAndView result;
		Record record;
		this.managerService.checkPrincipal();
		this.recordService.updateFinalModeRecords();

		record = this.recordService.findOne(recordId);
		Boolean canDelete = false;
		Manager manager = this.managerService.findByUserAccount(LoginService.getPrincipal());
		Collection<Record> recordsByManager = this.recordService.recordsByManager(manager);
		Assert.isTrue((recordsByManager.contains(record)) || (!recordsByManager.contains(record) && record.isFinalMode()));
		if(recordsByManager.contains(record) && !record.isFinalMode()){
			canDelete = true;
		}
		result = new ModelAndView("record/display");
		result.addObject("requestURI", "/workProgramme/record/manager/display.do");
		result.addObject("record", record);
		result.addObject("canDelete", canDelete);
		return result;
	}

	// Ancilliary methods
	// -----------------------------------------------------------

	private ModelAndView createEditModelAndView(
			final Record record) {
		ModelAndView result;

		result = this.createEditModelAndView(record, null);

		return result;
	}

	private ModelAndView createEditModelAndView(
			final Record record, final String message) {
		ModelAndView result;
		String requestURI;
		Collection<Artist> artists = new ArrayList<Artist>(this.artistService.freeArtists());
		AdminConfig adminConfig = this.adminConfigService.findOne();
		
		Collection<String> types = new ArrayList<String>(adminConfig.getTypes());
		
		result = new ModelAndView("record/editcreate");
		requestURI = "/workProgramme/record/manager/editcreate.do";
		result.addObject("record", record);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("artists", artists);
		result.addObject("types", types);
		
		return result;
	}
	
	private ModelAndView createEditModelAndView2(
			final Record record) {
		ModelAndView result;

		result = this.createEditModelAndView2(record, null);

		return result;
	}

	private ModelAndView createEditModelAndView2(
			final Record record, final String message) {
		ModelAndView result;
		String requestURI;
		Collection<Artist> artists = new ArrayList<Artist>();
		if(record.getArtist()!=null){
			artists.add(record.getArtist());
		}
		artists.addAll(this.artistService.freeArtists());
		AdminConfig adminConfig = this.adminConfigService.findOne();
		
		Collection<String> types = new ArrayList<String>(adminConfig.getTypes());
		
		result = new ModelAndView("record/edit");
		requestURI = "/workProgramme/record/manager/edit.do";
		result.addObject("record", record);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("artists", artists);
		result.addObject("types", types);
		
		return result;
	}

}
