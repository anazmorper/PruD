package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdminConfigService;
import services.AdministratorService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.AdminConfig;
import domain.Administrator;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------------

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private AdministratorService			administratorService;
	
	@Autowired
	private AdminConfigService		adminConfigService;
	


	// Constructors ---------------------------------------------------------------

	public MessageAdministratorController() {
		super();
	}

	// Listing methods -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Administrator administratorPrincipal = this.administratorService.findOneByPrincipal();
		Collection<Message> messagesActor = this.messageService.allMessagesByActor();

		result = new ModelAndView("message/list");
		result.addObject("messages", messagesActor);
		result.addObject("administratorPrincipal", administratorPrincipal);
		result.addObject("requestURI", "message/administrator/list.do");

		return result;

	}
	
	// ListByTopic

	@RequestMapping(value = "/listByTopic", method = RequestMethod.GET)
	public ModelAndView listByTopic(@RequestParam String topic) {

		ModelAndView result;
		Administrator administratorPrincipal = this.administratorService.findOneByPrincipal();
		Collection<Message> messagesActor = this.messageService.listingByTopicActor(topic, administratorPrincipal.getId());

		result = new ModelAndView("message/list");
		result.addObject("requestURI", "message/administrator/list.do");
		result.addObject("messages", messagesActor);
		result.addObject("administratorPrincipal", administratorPrincipal);

		return result;

	}
	
	@RequestMapping(value = "/listByPriority", method = RequestMethod.GET)
	public ModelAndView listByPriority(@RequestParam String priority) {

		ModelAndView result;
		Administrator administratorPrincipal = this.administratorService.findOneByPrincipal();
		Collection<Message> messagesActor = this.messageService.listingByPriorityActor(priority, administratorPrincipal.getId());

		result = new ModelAndView("message/list");
		result.addObject("requestURI", "message/administrator/list.do");
		result.addObject("messages", messagesActor);
		result.addObject("administratorPrincipal", administratorPrincipal);

		return result;

	}

	// ListBySender

	@RequestMapping(value = "/listBySender", method = RequestMethod.GET)
	public ModelAndView listBySender(@RequestParam int actorId) {

		ModelAndView result;
		Administrator administratorPrincipal = this.administratorService.findOneByPrincipal();
		Actor actor = this.actorService.findOne(actorId);
		Collection<Message> messages = this.messageService.findAll();
		Collection<Message> messagesFinal = new ArrayList<Message>();
		for (Message m : messages) {
			if ((m.getRecipient().equals(administratorPrincipal) && m.getSender().equals(actor))
					|| (administratorPrincipal.equals(actor) && (m.getSender().equals(administratorPrincipal)))){
				messagesFinal.add(m);
			}
		}
		
		result = new ModelAndView("message/list");
		result.addObject("requestURI", "message/administrator/listBySender.do");
		result.addObject("messages", messagesFinal);
		result.addObject("administratorPrincipal", administratorPrincipal);

		return result;

	}

	// ListByRecipient

	@RequestMapping(value = "/listByRecipient", method = RequestMethod.GET)
	public ModelAndView listByRecipient(@RequestParam int actorId) {

		ModelAndView result;
		Administrator administratorPrincipal = this.administratorService.findOneByPrincipal();
		Actor actor = this.actorService.findOne(actorId);
		Collection<Message> messages = this.messageService.findAll();
		Collection<Message> messagesFinal = new ArrayList<Message>();
		for (Message m : messages) {
			if ((m.getSender().equals(administratorPrincipal) && m.getRecipient().equals(actor))
					|| (administratorPrincipal.equals(actor) && (m.getRecipient().equals(administratorPrincipal)))){
				messagesFinal.add(m);
			}
		}
		
		result = new ModelAndView("message/list");
		result.addObject("requestURI", "message/administrator/listByRecipient.do");
		result.addObject("messages", messagesFinal);
		result.addObject("administratorPrincipal", administratorPrincipal);

		return result;

	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageId) {

		ModelAndView result;
		Message message;
		Administrator administratorPrincipal = this.administratorService.findOneByPrincipal();

		message = this.messageService.findOne(messageId);
		
		Actor actorPrincipal = actorService.findByPrincipal();
		boolean isOwn = false;
		if (message.getRecipient().equals(actorPrincipal) || message.getSender().equals(actorPrincipal)){
			isOwn=true;
		}
		Assert.isTrue(isOwn);
		
		result = new ModelAndView("message/display");
		result.addObject("messageDisplay", message);
		result.addObject("administratorPrincipal", administratorPrincipal);

		return result;
	}

	// Creation and edition methods ------------------------------------


	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteMessage(@RequestParam int messageId) {
		ModelAndView result;
		Message messageToDelete;
		messageToDelete = this.messageService.findOne(messageId);
		try {
			this.messageService.delete(messageToDelete);
			result = new ModelAndView("message/list");
		} catch (Throwable oops) {
			result = this.createNewModelAndView(messageToDelete, "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;
		message = this.messageService.create();
		result = this.createNewModelAndView(message);
//		result.addObject("requestURI", "message/administrator/list.do");

		return result;

	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@ModelAttribute("m") @Valid Message m, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createNewModelAndView(m);
		else
			try {
				this.messageService.save(m);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createNewModelAndView(m, "message.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/sendBroadcast", method = RequestMethod.GET)
	public ModelAndView createBroadcast() {
		ModelAndView result;
		Message message;
		message = this.messageService.create();
		message.setRecipient(this.administratorService.findByPrincipal());
		result = this.createNewModelAndViewBroadcast(message);
//		result.addObject("requestURI", "message/administrator/list.do");

		return result;

	}
	
	@RequestMapping(value = "/sendBroadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView sendBroadcastAllActors(@ModelAttribute("m") @Valid Message m, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createNewModelAndViewBroadcast(m);
		else
			try {

				this.messageService.sendNotificationBroadcastAllActors(m.getSubject(), m.getBody(), m.getTopic(), m.getPriority());
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {

				result = this.createNewModelAndViewBroadcast(m, "message.commit.error");

			}
		return result;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam int messageId) {
		ModelAndView result;
		Message message, aux;
		aux = this.messageService.findOne(messageId);
		message = this.messageService.create();
		Assert.notNull(aux);
		
		Message messageReply = this.messageService.findOne(messageId);
		Actor actorPrincipal = actorService.findByPrincipal();
		boolean isOwn = false;
		if (messageReply.getRecipient().equals(actorPrincipal) || messageReply.getSender().equals(actorPrincipal)){
			isOwn=true;
		}
		Assert.isTrue(isOwn);
		
		message.setRecipient(aux.getSender());
		message.setSubject("Reply to:\"" + aux.getSubject() + "\"");
		message.setBody("\n-----------------\nSender: " + aux.getSender().getName() + "\n Recipient: " + aux.getRecipient().getName() + "\n Moment: " + aux.getMoment() + "\n Subject: " + aux.getSubject() + "\n Body: " + aux.getBody() + "\"\"");
		result = this.createNewModelAndView(message);
		result.addObject("requestURI", "message/administrator/send.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createNewModelAndView(Message m) {
		ModelAndView result;
		result = this.createNewModelAndView(m, null);
		return result;
	}

	protected ModelAndView createNewModelAndView(Message m, String message) {
		ModelAndView result;

		result = new ModelAndView("message/send");

		Actor actor = this.actorService.findByPrincipal();
		Collection<Actor> actors = this.actorService.findAll();
		AdminConfig adminConfig = this.adminConfigService.findOne();
		Collection<String> topicsES = new ArrayList<String>(adminConfig.getDefaultTopicsSpanish());
		Collection<String> topicsEN = new ArrayList<String>(adminConfig.getDefaultTopicsEnglish());
		Collection<String> priorityES = new ArrayList<String>();
		Collection<String> priorityEN = new ArrayList<String>();
		priorityEN.add("HIGH");priorityEN.add("NEUTRAL");priorityEN.add("LOW");
		priorityES.add("ALTA");priorityES.add("NEUTRAL");priorityES.add("BAJA");
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		
		topicsES = adminConfig.getDefaultTopicsSpanish();
		topicsEN = adminConfig.getDefaultTopicsEnglish();
		actors.remove(actor);

		result.addObject("actors", actors);
		result.addObject("topicsES", topicsES);
		result.addObject("topicsEN", topicsEN);
		result.addObject("priorityEN", priorityEN);
		result.addObject("priorityES", priorityES);
		result.addObject("language", language);
		result.addObject("message", message);
		result.addObject("m", m);
		return result;
	
	}
	
	protected ModelAndView createNewModelAndViewBroadcast(Message m) {
		ModelAndView result;
		result = this.createNewModelAndViewBroadcast(m, null);
		return result;
	}

	protected ModelAndView createNewModelAndViewBroadcast(Message m, String message) {
		ModelAndView result;

		result = new ModelAndView("message/sendBroadcast");

		Actor actor = this.actorService.findByPrincipal();
		Collection<Actor> actors = this.actorService.findAll();
		AdminConfig adminConfig = this.adminConfigService.findOne();
		Collection<String> topicsES = new ArrayList<String>(adminConfig.getDefaultTopicsSpanish());
		Collection<String> topicsEN = new ArrayList<String>(adminConfig.getDefaultTopicsEnglish());
		Collection<String> priorityES = new ArrayList<String>();
		Collection<String> priorityEN = new ArrayList<String>();
		priorityEN.add("HIGH");priorityEN.add("NEUTRAL");priorityEN.add("LOW");
		priorityES.add("ALTA");priorityES.add("NEUTRAL");priorityES.add("BAJA");
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		
		topicsES = adminConfig.getDefaultTopicsSpanish();
		topicsEN = adminConfig.getDefaultTopicsEnglish();
		actors.remove(actor);

		result.addObject("actors", actors);
		result.addObject("topicsES", topicsES);
		result.addObject("topicsEN", topicsEN);
		result.addObject("priorityEN", priorityEN);
		result.addObject("priorityES", priorityES);
		result.addObject("language", language);
		result.addObject("message", message);
		result.addObject("m", m);
		return result;
	
	}

	protected ModelAndView createReplyModelAndView(Message m) {
		ModelAndView result;
		result = this.createReplyModelAndView(m, null);
		return result;
	}

	protected ModelAndView createReplyModelAndView(Message m, String message) {
		ModelAndView result;
		AdminConfig adminConfig = new AdminConfig();
		Collection<String> topicsES = new ArrayList<String>();
		Collection<String> topicsEN = new ArrayList<String>();
		
		topicsES = adminConfig.getDefaultTopicsSpanish();
		topicsEN = adminConfig.getDefaultTopicsEnglish();

		result = new ModelAndView("message/send");

		result.addObject("topicsES", topicsES);
		result.addObject("topicsEN", topicsEN);
		result.addObject("message", message);
		result.addObject("m", m);
		return result;
	}

}