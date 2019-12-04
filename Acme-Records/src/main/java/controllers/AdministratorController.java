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

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import domain.Actor;
import domain.Administrator;


@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private AdministratorRepository			administratorRepository;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

	
	// Edition -----------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int adminId) {
			ModelAndView result;
			Administrator ad;
			UserAccount principal;
			String requestURI;

			ad = this.administratorService.findOne(adminId);
			principal = LoginService.getPrincipal();
			requestURI = "administrator/edit.do";
			Assert.isTrue(ad.getUserAccount().equals(principal));

			result = this.createEditModelAndView(ad);
			result.addObject("requestURI", requestURI);
			return result;
		}
		
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Administrator a, final BindingResult binding) {
			ModelAndView result;
			UserAccount userAccount;

			for (final ObjectError oe : binding.getAllErrors())
				System.out.println(oe);

			if (binding.hasErrors())
				result = this.createEditModelAndView(a);
			else
				try {
					Authority authority = new Authority();
					authority.setAuthority(Authority.ADMIN);
					a.getUserAccount().getAuthorities().add(authority);
					userAccount = super.hashSavePassword(a.getUserAccount());
					a.setUserAccount(userAccount);
					this.administratorService.save(a);
					
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					oops.printStackTrace();
					result = this.createEditModelAndView(a, "actor.commit.error");
				}
			return result;
		}
		
	//////-RECONSTRUCTOR
			@RequestMapping(value="editReconstructor", method=RequestMethod.POST, params = "save")
			public ModelAndView saveReconstructor(Administrator admin, BindingResult binding){
				ModelAndView result;
				Administrator a;
				
				a = administratorService.reconstruct( admin, binding);
				if (binding.hasErrors()){
					result  = createEditModelAndView(a);
				}else{
					try{
						administratorService.save(a);
						result = new ModelAndView("redirect:list.do");
					}catch(Throwable oops){
						result = createEditModelAndView(admin , "admin.commit.error");
					}
				}
				return result;
			}
	// Delete profile
			
	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.GET)
	public ModelAndView delete(final int adminId) {
		ModelAndView result;
			
		final Administrator administrator;
			
		administrator = administratorService.findOneByPrincipal();
		this.administratorService.delete(administrator);
				
		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}

			
	// Export to csv ----------------------------------------------------------------

	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		final Administrator administrator;
		final UserAccount us = LoginService.getPrincipal();
		administrator = this.administratorRepository
				.findByUserAccountId(us.getId());

		String csvFileName = "administrator.csv";

		response.setContentType("text/csv");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		String dat1 = administrator.getName();
		String dat2 = administrator.getSurname();
		String dat4 = administrator.getAddress();
		String dat5 = administrator.getPhoneNumber();
		String dat6 = administrator.getEmail();
		String dat7 = administrator.getPhoto();

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "Name", "Surname", "Address",
				"Phone", "Email", "Photo" };

		String[] body = { dat1, dat2, dat4, dat5, dat6, dat7 };

		csvWriter.writeHeader(header);
		csvWriter.writeHeader(body);

		csvWriter.close();
	}

		// Ancilliary methods -----------------------------------------------------------

		private ModelAndView createEditModelAndView(final Administrator a) {
			ModelAndView result;

			result = this.createEditModelAndView(a, null);

			return result;
		}

		private ModelAndView createEditModelAndView(final Administrator ad, final String message) {
			ModelAndView result;
			String requestURI;

			result = new ModelAndView("administrator/edit");
			requestURI = "administrator/edit.do";
			result.addObject("administrator", ad);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}

		
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display() {
			ModelAndView result;
			Administrator actor;
			actor = this.administratorService.findOneByPrincipal();
			Actor actorPrincipal = actorService.findByPrincipal();
			Assert.isTrue(actorPrincipal.equals(actor));
			result = new ModelAndView("administrator/display");
			result.addObject("requestURI", "/administrator/display.do");
			result.addObject("actor", actor);
			return result;

		}
}
