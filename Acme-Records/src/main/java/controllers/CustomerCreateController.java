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

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import services.CustomerService;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerCreateController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private UserAccountRepository		userAccountRepository;


	// Constructors -----------------------------------------------------------

	public CustomerCreateController() {
		super();
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.create();

		result = this.createEditModelAndView2(customer);

		return result;
	}

	
		// Edition -----------------------------------------------------------
		@RequestMapping(value = "/editcreate", method = RequestMethod.GET)
		public ModelAndView editcreate(@RequestParam final int customerId) {
			ModelAndView result;
			Customer customer;
			UserAccount principal;
			String requestURI;

			customer = this.customerService.findOne(customerId);
			principal = LoginService.getPrincipal();
			requestURI = "customer/editcreate.do";
			Assert.isTrue(customer.getUserAccount().equals(principal));

			result = this.createEditModelAndView2(customer);
			result.addObject("requestURI", requestURI);
			return result;
		}

		@RequestMapping(value = "/editcreate", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Customer customer, final BindingResult binding) {
			ModelAndView result;
			UserAccount userAccount;
			boolean usernameEquals = false;

			for (final ObjectError oe : binding.getAllErrors())
				System.out.println(oe);

			if (binding.hasErrors())
				result = this.createEditModelAndView2(customer);
			else
				try {
					Authority authority = new Authority();
					authority.setAuthority(Authority.CUSTOMER);
					customer.getUserAccount().getAuthorities().add(authority);
					String customerUserName = customer.getUserAccount().getUsername();
					
					for (UserAccount ua : this.userAccountRepository.findAll()){
						if (ua.getUsername().equals(customerUserName)){
							usernameEquals = true;
							break;
						}
					}
					userAccount = super.hashSavePassword(customer.getUserAccount());
					customer.setUserAccount(userAccount);
					this.customerService.saveC(customer);
					result = new ModelAndView("redirect:../welcome/index.do");
				} catch (final Throwable oops) {
					oops.printStackTrace();
					if (usernameEquals){
						result = this.createEditModelAndView2(customer, "actor.commit.error.usern");
					} else {
						result = this.createEditModelAndView2(customer, "customer.commit.error");
					}
					
				}
			return result;
		}
		

		// Ancilliary methods -----------------------------------------------------------

		private ModelAndView createEditModelAndView2(final Customer customer) {
			ModelAndView result;

			result = this.createEditModelAndView2(customer, null);

			return result;
		}

		private ModelAndView createEditModelAndView2(final Customer customer, final String message) {
			ModelAndView result;
			String requestURI;
			

			
			result = new ModelAndView("customer/editcreate");
			requestURI = "customer/editcreate.do";
			result.addObject("customer", customer);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}

}
