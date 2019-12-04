
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository -----------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private Validator validator;

	//Suporting Service -------------------------------------------

	//Simple CRUD methods -----------------------------------------
	public AdministratorService() {
		super();
	}

	public Administrator save(final Administrator a) {
		Assert.notNull(a);
		Assert.hasText(a.getName());
		Assert.hasText(a.getSurname());
		Assert.hasText(a.getEmail());
		Assert.notNull(a.getUserAccount());

		checkPrincipal();
		Administrator result;
		
		
		result = (Administrator) this.actorService.save(a);
		return result;
		
	}

	public Administrator create() {
		Administrator result;
		checkPrincipal();
		UserAccount userAccount;
		Authority authority;
		Collection<Authority> authorities;
		

		userAccount = this.userAccountService.create();
		userAccount.setActive(true);
		userAccount.setUsername("");
		userAccount.setPassword("");
		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result = new Administrator ();
		result.setUserAccount(userAccount);
		

		return result;
	}
	
	public void save2(final Administrator m) {
		Assert.notNull(m);
		Assert.hasText(m.getName());
		Assert.hasText(m.getSurname());
		Assert.hasText(m.getEmail());
		Assert.notNull(m.getUserAccount());
		this.administratorRepository.save(m);
	}

	public Administrator findOne(final int adminId) {
		Administrator res;

		res = this.administratorRepository.findOne(adminId);
		Assert.notNull(res);

		return res;
	}
	
	
	public void delete(final Administrator administrator) {
		assert administrator != null;
		assert administrator.getId() != 0;
		checkPrincipal();
		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));
		Administrator principal;
		principal = findOneByPrincipal();
		Assert.isTrue(administrator.equals(principal));
		this.administratorRepository.delete(administrator);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> r;

		Assert.notNull(this.administratorRepository);
		r = this.administratorRepository.findAll();
		Assert.notNull(r);

		return r;
	}

	//----------------------------------------------------

	public Administrator findByPrincipal() {

		Administrator h;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		h = this.findByUserAccount(userAccount);
		Assert.notNull(h);

		return h;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator a;
		a = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return a;
	}

	/*
	 * Find administrator whose userAccount is the same as the logged userAccount
	 */
	public Administrator findOneByPrincipal() {
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(result);
		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		Assert.isTrue(authorities.contains(auth));
	}
	
	public void checkNotPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		Assert.isTrue(!authorities.contains(auth));
	}

	
	//Reconstructor
	public Administrator reconstruct(Administrator a, BindingResult binding){
		Administrator result;
		
		if(a.getId() == 0){
			result = a;
		}else{
			result = administratorRepository.findOne(a.getId());
			result.setName(a.getName());
			result.setEmail(a.getEmail());
			validator.validate(result, binding);
		}
		return result;
	}
}
