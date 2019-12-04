/*
 * /*
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
import java.util.ArrayList;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ArtistService;
import services.ManagerService;
import domain.Actor;
import domain.Artist;
import domain.Manager;

@Controller
@RequestMapping("/artist/manager")
public class ManagerController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ManagerRepository	managerRepository;
	@Autowired
	private ArtistService		artistService;


	// Constructors -----------------------------------------------------------

	public ManagerController() {
		super();
	}

	// Export to csv ----------------------------------------------------------------

	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(final HttpServletResponse response) throws IOException {
		final Manager manager;
		final UserAccount us = LoginService.getPrincipal();
		manager = this.managerRepository.findByUserAccountId(us.getId());

		final String csvFileName = "manager.csv";

		response.setContentType("text/csv");

		final String headerKey = "Content-Disposition";
		final String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		final String dat1 = manager.getName();
		final String dat2 = manager.getSurname();
		final String dat4 = manager.getAddress();
		final String dat5 = manager.getPhoneNumber();
		final String dat6 = manager.getEmail();
		final String dat7 = manager.getPhoto();

		final ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		final String[] header = {
			"Name", "Surname", "Address", "Phone", "Email", "Photo"
		};

		final String[] body = {
			dat1, dat2, dat4, dat5, dat6, dat7
		};

		csvWriter.writeHeader(header);
		csvWriter.writeHeader(body);

		csvWriter.close();
	}

	// Edition -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int managerId) {
		ModelAndView result;
		Manager manager;
		UserAccount principal;
		String requestURI;

		manager = this.managerService.findOne(managerId);
		principal = LoginService.getPrincipal();
		requestURI = "artist/manager/edit.do";
		Assert.isTrue(manager.getUserAccount().equals(principal));

		result = this.createEditModelAndView(manager);
		result.addObject("requestURI", requestURI);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;
		UserAccount userAccount;

		for (final ObjectError oe : binding.getAllErrors())
			System.out.println(oe);

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				final Authority authority = new Authority();
				authority.setAuthority(Authority.MANAGER);
				manager.getUserAccount().getAuthorities().add(authority);
				userAccount = super.hashSavePassword(manager.getUserAccount());
				manager.setUserAccount(userAccount);
				this.managerService.save(manager);

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Manager> managers;
		managers = this.managerService.findAll();
		result = new ModelAndView("manager/list");
		result.addObject("managers", managers);

		result.addObject("requestURI", "artist/manager/list.do");
		return result;
	}

	//Delete profile

	@RequestMapping(value = "/deleteManager", method = RequestMethod.GET)
	public ModelAndView deleteProfile(final int managerId) {
		ModelAndView result;

		final Manager b;

		b = this.managerService.findOneByPrincipal();
		this.managerService.delete(b);

		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

	//////-RECONSTRUCTOR
	@RequestMapping(value = "editReconstructor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReconstructor(final Manager bro, final BindingResult binding) {
		ModelAndView result;
		Manager a;

		a = this.managerService.reconstruct(bro, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(a);
		else
			try {
				this.managerService.save(a);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(bro, "manager.commit.error");
			}
		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int managerId) {
		ModelAndView result;
		Manager manager;
		manager = this.managerService.findOne(managerId);

		final Actor actorPrincipal = this.actorService.findByPrincipal();
		Assert.isTrue(actorPrincipal.equals(manager));

		result = new ModelAndView("manager/display");
		result.addObject("requestURI", "/artist/manager/display.do");
		result.addObject("manager", manager);
		return result;

	}

	// Ancilliary methods -----------------------------------------------------------

	private ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;
		String requestURI;

		result = new ModelAndView("manager/edit");
		requestURI = "manager/edit.do";
		result.addObject("manager", manager);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

	@RequestMapping(value = "/listartist", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Artist> res;

		res = this.artistService.artistByActor();
		result = new ModelAndView("artist/list");
		result.addObject("requestURI", "manager/artist/listartist.do");
		result.addObject("artists", res);

		return result;
	}

	@RequestMapping(value = "/listMyArtists", method = RequestMethod.GET)
	public ModelAndView listMyArtits() {
		ModelAndView result;
		Collection<Artist> res = new ArrayList<Artist>();
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);

		res.addAll(this.artistService.artistByActor());
		result = new ModelAndView("manager/listMyArtists");
		result.addObject("requestURI", "manager/artist/listartist.do");
		result.addObject("artists", res);

		return result;
	}

}
