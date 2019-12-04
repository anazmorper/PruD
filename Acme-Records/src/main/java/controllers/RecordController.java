package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Artist;
import domain.Customer;
import domain.Manager;
import domain.Purchase;
import domain.Record;
import domain.WorkProgramme;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ArtistService;
import services.CustomerService;
import services.ManagerService;
import services.RecordService;
import services.WorkProgrammeService;

@Controller
@RequestMapping("/record")
public class RecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RecordService recordService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ArtistService artisService;
	
	@Autowired
	private WorkProgrammeService workProgrammeService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public RecordController() {
		super();
	}

	// Search -------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		this.recordService.updateFinalModeRecords();

		result = new ModelAndView("record/search");

		return result;
	}

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public ModelAndView results(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Record> records;
		this.recordService.updateFinalModeRecords();


		records = this.recordService.findRecordByKey(keyword);
		
		result = new ModelAndView("record/listAvailable");
		result.addObject("records", records);
		result.addObject("requestURI", "record/results.do");
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Record> records;
		this.recordService.updateFinalModeRecords();
		records = this.recordService.recordsFinalModeTrueAndVisibleWorkProgramme();
		result = new ModelAndView("record/list");
		result.addObject("requestURI", "record/list.do");
		result.addObject("record", records);
	

		return result;
	}
	
	@RequestMapping(value = "/listbyartist", method = RequestMethod.GET)
	public ModelAndView listbyartist(@RequestParam Integer artistId) {
		ModelAndView result;
		Record r;
		Artist artist;
		artist = artisService.findOne(artistId);
		this.recordService.updateFinalModeRecords();
		r = artist.getRecord();
		if (r.isFinalMode() == false){
			r = null;
		}
		result = new ModelAndView("record/listAvailable");
		result.addObject("requestURI", "record/listbyrecord.do");
		result.addObject("records", r);
	

		return result;
	}
	
	@RequestMapping(value = "/listbyartistaut", method = RequestMethod.GET)
	public ModelAndView listbyartistaut(@RequestParam Integer artistId) {
		ModelAndView result;
		Record r;
		Artist artist;
		artist = artisService.findOne(artistId);
		this.recordService.updateFinalModeRecords();
		r = artist.getRecord();
		result = new ModelAndView("record/listAvailable");
		result.addObject("requestURI", "record/listbyrecordaut.do");
		result.addObject("records", r);
	

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int recordId) {
		Actor a = null;
		Collection<Artist> artist = new ArrayList<Artist>();
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
			UserAccount u = LoginService.getPrincipal();
			a = this.actorService.findByUserAccount(u);
			artist = new ArrayList<Artist>(artisService.artistByActor());
		}
		Boolean verdadero = null;
		ModelAndView result;
		Record record;
		Record res = null;
		this.recordService.updateFinalModeRecords();
		
		record = this.recordService.findOne(recordId);
		
		if (a instanceof Customer) {
			
			final Customer c = this.customerService.findOneByPrincipal();
			Assert.notNull(c);
			if (artist.contains(record.getArtist())){
				verdadero = true;
			}
			if (record.isFinalMode()== true || verdadero == true){
				res = record;
				
			}
		}else if(a instanceof Manager) {
			final Manager m = this.managerService.findOneByPrincipal();
			Assert.notNull(m);
			if (artist.contains(record.getArtist())){
				verdadero = true;
			}
			if (record.isFinalMode()== true || verdadero == true){
				res = record;
				
			}
		}else{
			
			if (record.isFinalMode()== true){
				res = record;
				
			}
		}
		
	
		Assert.notNull(res);
		result = new ModelAndView("record/display");
		result.addObject("requestURI", "/record/display.do");
		result.addObject("record", res);
		
		return result;
	}
	
}
