
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ManagerService;
import services.CustomerService;
import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.Customer;

@Controller
@RequestMapping("personaldata/actor")
public class PersonalDataActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private CustomerService			customerService;

	//Edition ----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result = null;
		Actor actor;
		final Administrator admin;
		final Manager b;
		final Customer m;

		actor = this.actorService.findByPrincipal();

		if (actor instanceof Administrator) {
			admin = administratorService.findByPrincipal();
			result = new ModelAndView("redirect:/administrator/edit.do?adminId=" + admin.getId());
		} else if (actor instanceof Manager) {
			b = managerService.findOneByPrincipal();
			result = new ModelAndView("redirect:/manager/edit.do?managerId=" + b.getId());
		} else if (actor instanceof Customer) {
			m = customerService.findOneByPrincipal();
			result = new ModelAndView("redirect:/customer/edit.do?customerId=" + m.getId());
		} 
		
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Actor a, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(a);
			System.out.println(binding.getAllErrors());
		} else
			try {
				this.actorService.save(a);
				result = new ModelAndView("redirect:../../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(a, "actor.commit.error");
				System.out.println(oops.getLocalizedMessage());
			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Actor a, final BindingResult binding) {
		ModelAndView result;
		try {
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(a, "actor.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteActor", method = RequestMethod.GET)
	public ModelAndView delete(final int actorId) {
		ModelAndView result;
		Actor actor = this.actorService.findOne(actorId);
		assert actor != null;
		final Administrator administrator;
		final Manager b;
		final Customer m;
			
		if (actor instanceof Administrator) {
			administrator = administratorService.findOneByPrincipal();
			this.administratorService.delete(administrator);
		} else if (actor instanceof Manager) {
			b = managerService.findOneByPrincipal();
			this.managerService.delete(b);
		} else if (actor instanceof Customer) {
			m = customerService.findOneByPrincipal();
			this.customerService.delete(m);
		} 
		result = new ModelAndView("redirect:listAuthors.do");

		return result;
	}
	
	
	//Display ----------------------------------------

		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display() {
			ModelAndView result = null;
			Actor actor;
			final Administrator admin;
			final Manager b;
			final Customer m;

			actor = this.actorService.findByPrincipal();

			if (actor instanceof Administrator) {
				admin = administratorService.findByPrincipal();
				result = new ModelAndView("redirect:/administrator/display.do?adminId=" + admin.getId());
			} else if (actor instanceof Manager) {
				b = managerService.findOneByPrincipal();
				result = new ModelAndView("redirect:/manager/display.do?managerId=" + b.getId());
			} else if (actor instanceof Customer) {
				m = customerService.findOneByPrincipal();
				result = new ModelAndView("redirect:/customer/display.do?customerId=" + m.getId());
			} 
			return result;

		}

	//Ancillary methods ------------------------------
	protected ModelAndView createEditModelAndView(final Actor a) {
		ModelAndView result;
		result = this.createEditModelAndView(a, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor s, final String messageCode) {
		assert s != null;
		ModelAndView result = null;
		final Administrator administrator;
		final Manager b;
		final Customer m;
		
		if (s instanceof Administrator) {
			administrator = administratorService.findOneByPrincipal();
			result = new ModelAndView("administrator/edit");
			result.addObject("administrator", administrator);
			result.addObject("actor", s);
			result.addObject("message", messageCode);
		} else if (s instanceof Manager) {
			b = managerService.findOneByPrincipal();
			result = new ModelAndView("manager/edit");
			result.addObject("manager", b);
			result.addObject("actor", s);
			result.addObject("message", messageCode);
		} else if (s instanceof Customer) {
			m = customerService.findOneByPrincipal();
			result = new ModelAndView("customer/edit");
			result.addObject("customer", m);
			result.addObject("actor", s);
			result.addObject("message", messageCode);
			
		} 
		

		return result;

	}
}
