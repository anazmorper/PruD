
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ArtistService;
import services.CustomerService;
import services.ManagerService;
import services.RecordService;
import domain.Actor;
import domain.Administrator;
import domain.Artist;
import domain.Customer;
import domain.Manager;
import domain.Record;

@Controller
@RequestMapping("/artist")
public class ArtistController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ArtistService	artistService;

	@Autowired
	private RecordService	recordService;

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ManagerService managerService;


	// Constructors -----------------------------------------------------------

	public ArtistController() {
		super();
	}

	// Search -------------------------------------------------------

	@RequestMapping(value = "/listbyrecord", method = RequestMethod.GET)
	public ModelAndView listbyrecord(@RequestParam final Integer recordId) {
		ModelAndView result;
		Record r;
		Artist artist;

		r = this.recordService.findOne(recordId);

		if (r.isFinalMode() == true)
			artist = r.getArtist();
		else
			artist = null;
		result = new ModelAndView("artist/listAvailable");
		result.addObject("requestURI", "artist/listbyrecord.do");
		result.addObject("artist", artist);

		return result;
	}
	
	@RequestMapping(value = "/listbyrecordSimple", method = RequestMethod.GET)
	public ModelAndView listbyrecordSimple(@RequestParam final Integer recordId) {
		ModelAndView result;
		Record r;
		r = this.recordService.findOne(recordId);

		result = new ModelAndView("artist/listAvailable");
		result.addObject("requestURI", "artist/listbyrecord.do");
		result.addObject("artist", r.getArtist());

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Artist> res;

		res = this.artistService.findbyrecordFinalModeTrue();
		res.removeAll(Collections.singleton(null)); 
		result = new ModelAndView("artist/list");
		result.addObject("requestURI", "artist/list.do");
		result.addObject("artists", res);

		return result;
	}

	@RequestMapping(value = "/listbyrecordaut", method = RequestMethod.GET)
	public ModelAndView listbyrecordaut(@RequestParam final Integer recordId) {
		ModelAndView result;
		Record r;
		Artist artist;

		r = this.recordService.findOne(recordId);
		artist = r.getArtist();
		result = new ModelAndView("artist/listAvailable");
		result.addObject("requestURI", "artist/listbyrecordaut.do");
		result.addObject("artist", artist);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int artistId) {
		ModelAndView result;
		Actor a = null;
		Collection<Artist> artists = new ArrayList<Artist>();
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
		UserAccount u = LoginService.getPrincipal();
		a = this.actorService.findByUserAccount(u);
		artists = new ArrayList<Artist>(artistService.artistByActor());
		}
		Boolean verdadero = null;
		Artist artist;
		Artist res = null;
		artist = this.artistService.findOne(artistId);
		
		
if (null!= a && a instanceof Customer) {
			
	final Customer c = this.customerService.findOneByPrincipal();
	Assert.notNull(c);
	if (artists.contains(artist)){
		verdadero = true;
	}
	if ((null != artist.getRecord()&& artist.getRecord().isFinalMode()== true) 
			|| verdadero == true){
		res = artist;
		
	}
}else if(null!= a && a instanceof Manager){
	final Manager m = this.managerService.findOneByPrincipal();
	Assert.notNull(m);
	if (artists.contains(artist)){
		verdadero = true;
	}
	if ((null != artist.getRecord()&& artist.getRecord().isFinalMode()== true)
		|| verdadero == true){
		res = artist;
		
	}
		
	}else{
		
		if (null != artist.getRecord()&& artist.getRecord().isFinalMode()== true){
			res = artist;
			
		}
		
	}
	
		Assert.notNull(res);
		result = new ModelAndView("artist/display");
		result.addObject("requestURI", "/artist/display.do");
		result.addObject("artist", res);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createArtist() {
		final ModelAndView result;
		Artist artist;
		artist = this.artistService.create();
		result = this.createEditModelAndView(artist);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int artistId) {
		final ModelAndView result;
		Artist artist;
		Artist res = null;
		Actor principal;
		Collection<Artist> artistas = new ArrayList<Artist>(artistService.artistByActor());

		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);
		
		artist = this.artistService.findOne(artistId);
		if (artistas.contains(artist)){
		res = artist;	
		}
		Assert.notNull(res);
		result = this.createEditModelAndView2(res);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Artist artist, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView2(artist);
		else
			try {
				this.artistService.save(artist);
				result = new ModelAndView("redirect:/artist/manager/listMyArtists.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView2(artist, "purchase.commit.error");

			}

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Artist artist, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(artist);
		else
			try {
				this.artistService.save(artist);
				result = new ModelAndView("redirect:/artist/manager/listMyArtists.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(artist, "purchase.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int artistId) {
		ModelAndView result;
		Actor principal;
		final Artist artist;

		principal = this.actorService.findByPrincipal();

		Assert.isInstanceOf(Manager.class, principal);

		artist = this.artistService.findOne(artistId);
		this.artistService.delete(artist);
		result = new ModelAndView("redirect:/artist/manager/listMyArtists.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final Artist artist) {
		ModelAndView result;
		result = this.createEditModelAndView(artist, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Artist artist, final String message) {
		ModelAndView result;
		String requestURI;

		if (artist == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = new ModelAndView("artist/create");
		requestURI = "/torrente/edit.do";
		result.addObject("requestURI", requestURI);
		result.addObject("artist", artist);

		return result;
	}
	
	protected ModelAndView createEditModelAndView2(final Artist artist) {
		ModelAndView result;
		result = this.createEditModelAndView2(artist, null);
		return result;
	}

	protected ModelAndView createEditModelAndView2(final Artist artist, final String message) {
		ModelAndView result;
		String requestURI;

		if (artist == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = new ModelAndView("artist/edit");
		requestURI = "/torrente/edit.do";
		result.addObject("requestURI", requestURI);
		result.addObject("artist", artist);

		return result;
	}
}