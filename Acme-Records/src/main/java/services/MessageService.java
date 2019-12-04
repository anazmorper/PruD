
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Customer;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository			messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;
	
	@Autowired
	private CustomerService				authorService;

	@Autowired
	private AdministratorService		administratorService;


	// Constructors -----------------------------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		final Actor sender = this.actorService.findByPrincipal();
		Message message;
		message = new Message();
		message.setMoment(new Date());
		message.setSender(sender);
		return message;
	}
	public Message findOne(final int messageId) {
		Assert.isTrue(messageId != 0);

		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;
		result = this.messageRepository.findAll();
		return result;

	}

	public Message save(final Message message) {

		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipient());
		Assert.notNull(message);
		Message result;
		Date current;

		current = new Date(System.currentTimeMillis() - 1000);
		message.setMoment(current);

		result = this.messageRepository.save(message);
		this.actorService.save(result.getSender());
		this.actorService.save2(result.getRecipient());
		return result;

	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		this.messageRepository.delete(message);
	}
	
	
	// Other business methods -------------------------------------------------
	public Collection<Message> recipientAllByActor(final int idActor) {
		//Todos los mensajes recibidos de un actor
		Collection<Message> res;
		res = this.messageRepository.recipientAllByActor(idActor);
		return res;

	}
	public Collection<Message> senderAllByActor(final int idActor) {
		//Todos los mensajes enviados de un actor
		Collection<Message> res;
		res = this.messageRepository.senderAllByActor(idActor);
		return res;

	}
	
	public Collection<Message> listingByTopicActor(String topic, final int idActor){
		//Todos los mensajes ordenados por tema de un actor
		Collection<Message> res;
		res = this.messageRepository.listingByTopicActor(topic, idActor);
		return res;

	}
	
	public Collection<Message> listingByPriorityActor(String p, final int idActor){
		//Todos los mensajes ordenados por prioridad de un actor
		Collection<Message> res;
		res = this.messageRepository.listingByPriorityActor(p, idActor);
		return res;

	}
	
	public Collection<Message> allMessagesByActor(){
		Actor principal = this.actorService.findByPrincipal();
		Collection<Message> result = new ArrayList<Message>();
		Collection<Message> allMessages = new ArrayList<Message>(findAll());
		for(Message m: allMessages){
			if(m.getSender().equals(principal) || m.getRecipient().equals(principal))
				result.add(m);
		}
		return result;
	}

	public void sendNotificationBroadcastAllActors(String subject, String body, String topic, String priority) {
		Assert.notNull(topic);
		Assert.notNull(priority);
		Collection<Actor> allActors;
		Actor sender;

		sender = this.actorService.findByPrincipal();
		allActors = this.actorService.findAll();
		allActors.remove(sender);

		for (Actor recipient : allActors) {
			Message messageToActor = new Message();
			Date current = new Date(System.currentTimeMillis() - 1000);

			messageToActor.setSubject(subject);
			messageToActor.setBody(body);
			messageToActor.setTopic(topic);
			messageToActor.setPriority(priority);
			messageToActor.setMoment(current);
			messageToActor.setSender(sender);
			messageToActor.setRecipient(recipient);
			
			this.messageRepository.save(messageToActor);
		}
	}
	
	public Collection<Message> findBySender(){
		Collection<Message> result = new ArrayList<Message>();
		Actor principal = this.actorService.findByPrincipal();
		Collection<Message> messages = new ArrayList<Message>(allMessagesByActor());
		for(Message message: messages){
			if(message.getSender().equals(principal))
				result.add(message);
		}
		
		return result;
	}
	
	public Collection<Message> findByRecipient(){
		Collection<Message> result = new ArrayList<Message>();
		Actor principal = this.actorService.findByPrincipal();
		Collection<Message> messages = new ArrayList<Message>(allMessagesByActor());
		for(Message message: messages){
			if(message.getRecipient().equals(principal))
				result.add(message);
		}
		
		return result;
	}
	
	public Collection<Message> findByTopic(String topic){
		Collection<Message> result = new ArrayList<Message>();
		Collection<Message> messages = new ArrayList<Message>(allMessagesByActor());
		for(Message message: messages){
			if(message.getTopic().equals(topic))
				result.add(message);
		}
		
		return result;
	}
	
	public Collection<Message> findByPriority(String p){
		Collection<Message> result = new ArrayList<Message>();
		Collection<Message> messages = new ArrayList<Message>(allMessagesByActor());
		for(Message message: messages){
			if(message.getPriority().equals(p))
				result.add(message);
		}
		
		return result;
	}
}
