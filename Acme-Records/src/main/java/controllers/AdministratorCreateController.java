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
import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorCreateController extends AbstractController {

	// Services ---------------------------------
	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private UserAccountRepository		userAccountRepository;


	// Constructors -----------------------------------------------------------

	public AdministratorCreateController() {
		super();
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator b;

		b = this.administratorService.create();

		result = this.createEditModelAndView2(b);

		return result;
	}

	
		// Edition -----------------------------------------------------------
		@RequestMapping(value = "/editcreate", method = RequestMethod.GET)
		public ModelAndView editcreate(@RequestParam final int administratorId) {
			ModelAndView result;
			Administrator a;
			UserAccount principal;
			String requestURI;

			a = this.administratorService.findOne(administratorId);
			principal = LoginService.getPrincipal();
			requestURI = "administrator/editcreate.do";
			Assert.isTrue(a.getUserAccount().equals(principal));

			result = this.createEditModelAndView2(a);
			result.addObject("requestURI", requestURI);
			return result;
		}

		@RequestMapping(value = "/editcreate", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Administrator b, final BindingResult binding) {
			ModelAndView result;
			UserAccount userAccount;
			boolean usernameEquals = false;

			for (final ObjectError oe : binding.getAllErrors())
				System.out.println(oe);

			if (binding.hasErrors())
				result = this.createEditModelAndView2(b);
			else
				try {
					Authority authority = new Authority();
					authority.setAuthority(Authority.ADMIN);
					b.getUserAccount().getAuthorities().add(authority);
					String userName = b.getUserAccount().getUsername();
					
					for (UserAccount ua : this.userAccountRepository.findAll()){
						if (ua.getUsername().equals(userName)){
							usernameEquals = true;
							break;
						}
					}
					userAccount = super.hashSavePassword(b.getUserAccount());
					b.setUserAccount(userAccount);
					this.administratorService.save(b);
					result = new ModelAndView("redirect:../welcome/index.do");
				} catch (final Throwable oops) {
					oops.printStackTrace();
					if (usernameEquals){
						result = this.createEditModelAndView2(b, "actor.commit.error.usern");
					} else {
						result = this.createEditModelAndView2(b, "actor.commit.error");
					}
				}
			return result;
		}
		
		
		
		// Ancilliary methods -----------------------------------------------------------

		private ModelAndView createEditModelAndView2(final Administrator b) {
			ModelAndView result;

			result = this.createEditModelAndView2(b, null);

			return result;
		}

		private ModelAndView createEditModelAndView2(final Administrator b, final String message) {
			ModelAndView result;
			String requestURI;
			

			
			result = new ModelAndView("administrator/editcreate");
			requestURI = "administrator/editcreate.do";
			result.addObject("administrator", b);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}

}
