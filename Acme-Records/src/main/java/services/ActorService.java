
package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Customer;

@Service
@Transactional
public class ActorService {

	// Managed Repository -----------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private AdminConfigService adminConfigService;
	

	// Simple CRUD methods -----------------------------------------
	public ActorService() {
		super();
	}

	public Actor findOne(final int actorId) {
		Assert.notNull(actorId);
		final Actor actor = this.actorRepository.findOne(actorId);
		return actor;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> r;

		Assert.notNull(this.actorRepository);
		r = this.actorRepository.findAll();
		Assert.notNull(r);

		return r;
	}

	public void ban(final int actorId) {

		final Actor actor = this.findOne(actorId);
		Assert.notNull(actor);

		actor.getUserAccount().setActive(false);
		this.actorRepository.save(actor);

	}

	public void unban(final int actorId) {
		final Actor actor = this.findOne(actorId);
		Assert.notNull(actor);
		actor.getUserAccount().setActive(true);
		this.actorRepository.save(actor);
	}

	// ----------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		try {
			userAccount = LoginService.getPrincipal();
			result = this.findByUserAccount(userAccount);
		} catch (final Throwable exc) {
			result = null;
		}
		return result;
	}

	public Actor findOneByPrincipal() {
		Actor result;
		result = this.actorRepository.findOneByPrincipal(LoginService.getPrincipal().getId());
		Assert.notNull(result);
		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Assert.hasText(actor.getName());
		Assert.hasText(actor.getSurname());
		Assert.hasText(actor.getEmail());
		Assert.notNull(actor.getUserAccount());

		final Actor result;

		//Tenemos que comprobar si el actor es nuevo, o lo estamos editando
		//String s = this.adminConfigService.getDefaultCountryCode();
		
		
		/*if (actor.getPhoneNumber().startsWith(s + " ")){
			//Si viene con patron defecto no hago nada
		
		}else{
			if (actor.getPhoneNumber().length() == 13){
				
				if (actor.getPhoneNumber().charAt(0) == '+' && 
					actor.getPhoneNumber().charAt(3) == ' ' ){
					
					actor.setPhoneNumber( actor.getPhoneNumber());
					
				}
				
			} else if(actor.getPhoneNumber().length() == 9){
			
			actor.setPhoneNumber(s + ' ' + actor.getPhoneNumber());
			
			}else if(actor.getPhoneNumber().length() == 14){
				if (actor.getPhoneNumber().charAt(0) == '+' && 
						actor.getPhoneNumber().charAt(4) == ' ' ){
						
						actor.setPhoneNumber( actor.getPhoneNumber());
						
					}
			}else if(actor.getPhoneNumber().length() == 12){
				if (actor.getPhoneNumber().charAt(0) == '+' && 
						actor.getPhoneNumber().charAt(2) == ' ' ){
						
						actor.setPhoneNumber( actor.getPhoneNumber());
						
					}
			}
		}
		*/	
		
		if (actor.getPhoneNumber() != null) {
			if(actor.getPhoneNumber().length()<4){
				
			}
			String phoneNumber = actor.getPhoneNumber();
			final String phoneCountryCode = this.adminConfigService.findConf().getDefaultCountryCode();
			if (!actor.getPhoneNumber().isEmpty() && !actor.getPhoneNumber().startsWith("+"))
				phoneNumber = phoneCountryCode + " " + phoneNumber;
			actor.setPhoneNumber(phoneNumber);
		}
		
		
		//Si es nuevo...
		if (actor.getId() == 0) {
			UserAccount userAccount;
			final UserAccount savedUserAccount;

			userAccount = actor.getUserAccount();
			
			Assert.notNull(userAccount.getUsername());
			Assert.notNull(userAccount.getPassword());
			actor.setUserAccount(userAccount);
			
			
			result = this.actorRepository.save(actor);
		} else {
			UserAccount principal, userAccount;
			final UserAccount savedUserAccount;
			final Md5PasswordEncoder encoder;

			principal = LoginService.getPrincipal();
			userAccount = actor.getUserAccount();
			
			
			Assert.notNull(principal); //Comprobamos que hay un Actor logeado en el sistema, que puede o no ser el mismo que queremos guardar

			
			result = this.actorRepository.save(actor);
		}
		return result;
	}
	
	public Actor save2(final Actor actor){
		Assert.notNull(actor);
		Assert.hasText(actor.getName());
		Assert.hasText(actor.getSurname());
		Assert.hasText(actor.getEmail());
		Assert.notNull(actor.getUserAccount());

		final Actor result;
		
		result = this.actorRepository.save(actor);
		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public boolean isAuthenticated() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount != null)
			result = true;
		return result;
	}

	public Actor checkPrincipal(final UserAccount userAccount) {
		Actor actor;
		UserAccount principalUserAccount;
		principalUserAccount = LoginService.getPrincipal();
		Assert.isTrue(principalUserAccount.equals(userAccount));
		actor = this.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(principalUserAccount.equals(userAccount));
		return actor;
	}
}
