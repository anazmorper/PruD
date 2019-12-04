package controllers;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Manager;
import domain.Record;
import services.ManagerService;
import services.PurchaseService;
import services.RecordService;
import services.WorkProgrammeService;





@Controller

@RequestMapping(value = "/administrator")

public class AdministratorDashboardController extends AbstractController {



	//Services---------------------
	
	@Autowired
	private RecordService	recordService;
	
	@Autowired
	private WorkProgrammeService	workProgrammeService;
	
	@Autowired
	private PurchaseService	purchaseService;
	
	@Autowired
	private ManagerService	managerService;
	

	//Displaying----------------------



	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)

		public ModelAndView display() {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");
		
		Double[] avgMinMaxStRecordByWorkProgramme = this.recordService.avgMinMaxStRecordByWorkProgramme();
		Double[] avgMinMaxStRecordPrice = this.recordService.avgMinMaxStRecordPrice();
		Double[] avgMinMaxStWorkProgrammeByManager = this.workProgrammeService.avgMinMaxStWorkProgrammeByManager();
		Double[] avgMinMaxStPurchasesByCustomer = this.purchaseService.avgMinMaxStPurchasesByCustomer();
		Collection<Record> findTop3RecordsByPurchase = this.recordService.findTop3RecordsByPurchase();
		Record findTopRecord = this.recordService.findTopRecord();
		Manager findTopManager = this.managerService.findTopManager();
		Collection<Record> findTop3RecordsByMoney = this.recordService.findTop3RecordsByMoney();


		
		result.addObject("avgMinMaxStRecordByWorkProgramme", avgMinMaxStRecordByWorkProgramme);
		result.addObject("avgMinMaxStRecordPrice", avgMinMaxStRecordPrice);
		result.addObject("avgMinMaxStWorkProgrammeByManager", avgMinMaxStWorkProgrammeByManager);
		result.addObject("avgMinMaxStPurchasesByCustomer", avgMinMaxStPurchasesByCustomer);
		result.addObject("findTop3RecordsByPurchase", findTop3RecordsByPurchase);
		result.addObject("findTopRecord", findTopRecord);
		result.addObject("findTopManager", findTopManager);
		result.addObject("findTop3RecordsByMoney", findTop3RecordsByMoney);
		
		return result;
	}


}