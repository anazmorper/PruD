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
import services.AdminConfigService;
import services.ArtistService;
import services.CustomerService;
import domain.Actor;
import domain.Artist;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private CustomerRepository		customerRepository;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private AdminConfigService adminConfigService;

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}


// Delete profile
	
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET)
	public ModelAndView delete(final int customerId) {
		ModelAndView result;

		final Customer m;
		
		m = customerService.findOneByPrincipal();
		this.customerService.delete(m);
		
		result = new ModelAndView("redirect:/j_spring_security_logout");

		return result;
	}
	
// Export to csv ----------------------------------------------------------------

	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		final Customer customer;
		final UserAccount us = LoginService.getPrincipal();
		customer = this.customerRepository.findByUserAccountId(us.getId());
		
		String csvFileName = "customer.csv";

		response.setContentType("text/csv");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		String dat1 = customer.getName();
		String dat2 = customer.getSurname();
		String dat4 = customer.getAddress();
		String dat5 = customer.getPhoneNumber();
		String dat6 = customer.getEmail();
		String dat7 = customer.getPhoto();


		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "Name", "Surname", "Address",
				"Phone", "Email", "Photo" };
		
		String[] body = {dat1, dat2, dat4, dat5, 
				dat6, dat7};

		csvWriter.writeHeader(header);
		csvWriter.writeHeader(body);

		csvWriter.close();
	}


		// Edition -----------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int customerId) {
			ModelAndView result;
			Customer customer;
			UserAccount principal;
			String requestURI;
			Collection<String> creditCardBrands = this.adminConfigService
					.creditCardNames();

			customer = this.customerService.findOne(customerId);
			principal = LoginService.getPrincipal();
			requestURI = "customer/edit.do";
			Assert.isTrue(customer.getUserAccount().equals(principal));

			result = this.createEditModelAndView(customer);
			result.addObject("requestURI", requestURI);
			result.addObject("creditCardBrands", creditCardBrands);
			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Customer customer, final BindingResult binding) {
			ModelAndView result;
			UserAccount userAccount;

			for (final ObjectError oe : binding.getAllErrors())
				System.out.println(oe);

			if (binding.hasErrors())
				result = this.createEditModelAndView(customer);
			else
				try {
					Authority authority = new Authority();
					authority.setAuthority(Authority.CUSTOMER);
					customer.getUserAccount().getAuthorities().add(authority);
					userAccount = super.hashSavePassword(customer.getUserAccount());
					customer.setUserAccount(userAccount);
					this.customerService.save(customer);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					oops.printStackTrace();
					result = this.createEditModelAndView(customer, "customer.commit.error");
				}
			return result;
		}
		
		// Edition -----------------------------------------------------------
				@RequestMapping(value = "/editCC", method = RequestMethod.GET)
				public ModelAndView editCC() {
					ModelAndView result;
					Customer customer;
					UserAccount principal;
					String requestURI;

					customer = this.customerService.findOneByPrincipal();
					principal = LoginService.getPrincipal();
					requestURI = "customer/editCC.do";
					Assert.isTrue(customer.getUserAccount().equals(principal));

					result = this.createEditModelAndViewCC(customer);
					result.addObject("requestURI", requestURI);
					
					return result;
				}

				@RequestMapping(value = "/editCC", method = RequestMethod.POST, params = "save")
				public ModelAndView saveCC(@Valid final Customer customer, final BindingResult binding) {
					ModelAndView result;

					for (final ObjectError oe : binding.getAllErrors())
						System.out.println(oe);

					if (binding.hasErrors())
						result = this.createEditModelAndViewCC(customer);
					else
						try {
							Customer old =this.customerService.findOne(customer.getId());
							customer.setUserAccount(old.getUserAccount());
							this.customerService.save(customer);
							result = new ModelAndView("redirect:/welcome/index.do");
						} catch (final Throwable oops) {
							oops.printStackTrace();
							result = this.createEditModelAndViewCC(customer, "customer.commit.error");
						}
					return result;
				}
		
	//////-RECONSTRUCTOR
			@RequestMapping(value="editReconstructor", method=RequestMethod.POST, params = "save")
			public ModelAndView saveReconstructor(Customer object, BindingResult binding){
				ModelAndView result;
				Customer a;
						
				a = customerService.reconstruct( object, binding);
				if (binding.hasErrors()){
					result  = createEditModelAndView(a);
				}else{
					try{
						customerService.save(a);
						result = new ModelAndView("redirect:list.do");
					}catch(Throwable oops){
						result = createEditModelAndView(object , "customer.commit.error");
					}
				}
				return result;
			}
		
		// Ancilliary methods -----------------------------------------------------------

		private ModelAndView createEditModelAndView(final Customer customer) {
			ModelAndView result;

			result = this.createEditModelAndView(customer, null);

			return result;
		}

		private ModelAndView createEditModelAndView(final Customer customer, final String message) {
			ModelAndView result;
			String requestURI;
			

			
			result = new ModelAndView("customer/edit");
			requestURI = "customer/edit.do";
			result.addObject("customer", customer);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}
		
		private ModelAndView createEditModelAndViewCC(final Customer customer) {
			ModelAndView result;

			result = this.createEditModelAndViewCC(customer, null);

			return result;
		}

		private ModelAndView createEditModelAndViewCC(final Customer customer, final String message) {
			ModelAndView result;
			String requestURI;
			Collection<String> creditCardBrands = this.adminConfigService
					.creditCardNames();
			
			result = new ModelAndView("customer/editCC");
			requestURI = "customer/editCC.do";
			result.addObject("customer", customer);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);
			result.addObject("creditCardBrands", creditCardBrands);
			return result;
		}
		
		// Display
		
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display() {
			ModelAndView result;
			Customer actor;
			actor = this.customerService.findOneByPrincipal();
			
			Actor actorPrincipal = actorService.findByPrincipal();
			Assert.isTrue(actorPrincipal.equals(actor));
			
			result = new ModelAndView("customer/display");
			result.addObject("requestURI", "/customer/display.do");
			result.addObject("actor", actor);
			return result;

		}

		
	
}
