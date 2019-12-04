
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminConfigService;
import controllers.AbstractController;
import domain.AdminConfig;

@Controller
@RequestMapping("/adminconfig/administrator")
public class AdminConfigAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AdminConfigService	adminConfigService;


	// Constructors -----------------------------------------------------------
	public AdminConfigAdministratorController() {
		super();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final AdminConfig c;

		c = this.adminConfigService.findConf();
		Assert.notNull(c);
		result = this.createEditModelAndView(c);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdminConfig adminConfig, final BindingResult binding) {
		ModelAndView result;

		for (final ObjectError error : binding.getAllErrors())
			System.out.println("Un error es: " + error.toString());

		if (binding.hasErrors())
			result = this.createEditModelAndView(adminConfig);
		else
			try {
				this.adminConfigService.save(adminConfig);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(adminConfig, "adminconfig.commit.error");
			}
		return result;
	}

	// Ancilliary methods ---------------------------------------------------------------
	private ModelAndView createEditModelAndView(final AdminConfig adminConfig) {
		ModelAndView result;

		result = this.createEditModelAndView(adminConfig, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final AdminConfig adminConfig, final String message) {
		ModelAndView result;

		result = new ModelAndView("adminconfig/edit");
		result.addObject("adminConfig", adminConfig);
		result.addObject("message", message);

		return result;
	}

}
