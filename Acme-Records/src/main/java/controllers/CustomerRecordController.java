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

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ArtistService;
import services.CustomerService;
import services.RecordService;
import domain.Actor;
import domain.Artist;
import domain.Customer;
import domain.Record;

@Controller
@RequestMapping("/customer/record")
public class CustomerRecordController extends AbstractController {

	// Services ---------------------------------

	@Autowired
	private RecordService recordService;
	
	@Autowired
	private ArtistService artistService;

	
	@RequestMapping(value = "/listrecord", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Record> res;;
		
		res = artistService.recordsByActor();
		result = new ModelAndView("record/listaut");
		result.addObject("requestURI", "customer/record/listrecord.do");
		result.addObject("record", res);
	
		return result;
	}
	
		
	
}
