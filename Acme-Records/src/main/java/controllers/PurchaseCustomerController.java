
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminConfigService;
import services.CustomerService;
import services.PurchaseService;
import services.RecordService;
import domain.CreditCard;
import domain.Customer;
import domain.Purchase;
import domain.Record;

@Controller
@RequestMapping("/customer/purchase")
public class PurchaseCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PurchaseService		purchaseService;
	@Autowired
	private RecordService		recordService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private AdminConfigService	adminConfigService;


	// Constructors -----------------------------------------------------------

	public PurchaseCustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Purchase> purchases;

		purchases = this.purchaseService.findPurchasesByCustomerLogged();
		result = new ModelAndView("purchase/list");
		result.addObject("requestURI", "customer/purchase/list.do");
		result.addObject("purchases", purchases);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Purchase purchase;
		final Collection<Record> records = this.recordService.findRecordsAvailableToPurchase();
		purchase = this.purchaseService.create();
		result = this.createEditModelAndView(purchase);
		result.addObject("records", records);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Purchase purchase, final BindingResult binding) {
		ModelAndView result;
		final Collection<Record> records = this.recordService.findRecordsAvailableToPurchase();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(purchase);
			result.addObject("records", records);
		} else
			try {
				this.purchaseService.saveCreate(purchase);
				result = new ModelAndView("redirect:/customer/purchase/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(purchase, "purchase.commit.error");
				result.addObject("records", records);
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Purchase purchase) {
		ModelAndView result;

		result = this.createEditModelAndView(purchase, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Purchase purchase, final String message) {

		ModelAndView result;

		if (purchase == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			final Collection<String> creditCardBrandsAux = this.adminConfigService.creditCardNames();
			final Collection<String> creditCardBrands = this.adminConfigService.creditCardNames();
			final Customer principal = this.customerService.findOneByPrincipal();
			CreditCard cc = null;
			if (null != principal.getCreditCard()) {
				cc = principal.getCreditCard();
				creditCardBrands.clear();
				creditCardBrandsAux.remove(cc.getBrandName());
				creditCardBrands.add(cc.getBrandName());
				creditCardBrands.addAll(creditCardBrandsAux);
			}
			result = new ModelAndView("purchase/create");

			result.addObject("creditCardBrands", creditCardBrands);
			result.addObject("cc", cc);
		}
		result.addObject("actionURL", "customer/purchase/create.do");
		result.addObject("purchase", purchase);
		result.addObject("message", message);

		return result;
	}

}
