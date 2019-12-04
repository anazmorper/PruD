
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository -----------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	
	@Autowired
	private AdministratorService		administratorService;
	


	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----------------------------------------
	public CustomerService() {
		super();
	}

	public Customer save(final Customer m) {
		Assert.notNull(m);
		checkPrincipal();
		Customer result;

		result = (Customer) this.actorService.save(m);

		return result;
		
	}
	
	public Customer saveC(final Customer m) {
		Assert.notNull(m);
		Customer result;

		result = (Customer) this.actorService.save(m);

		return result;
		
	}
	
	public void save2(final Customer m) {
		Assert.notNull(m);
		this.administratorService.checkNotPrincipal();
		Assert.hasText(m.getName());
		Assert.hasText(m.getSurname());
		Assert.hasText(m.getEmail());
		Assert.notNull(m.getUserAccount());
		this.customerRepository.save(m);
	}

	public Customer create() {

			Customer result;
			UserAccount userAccount;
			Authority authority;
			Collection<Authority> customerities;
			

			userAccount = this.userAccountService.create();
			userAccount.setActive(true);
			userAccount.setUsername("");
			userAccount.setPassword("");
			authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);
			customerities = new ArrayList<Authority>();
			customerities.add(authority);
			userAccount.setAuthorities(customerities);

			result = new Customer();
			result.setUserAccount(userAccount);
			

			return result;

	}

	public Customer findOne(final int m) {
		Customer res;

		res = this.customerRepository.findOne(m);
		Assert.notNull(res);

		return res;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> r;

		Assert.notNull(this.customerRepository);
		r = this.customerRepository.findAll();
		Assert.notNull(r);

		return r;
	}
	
	public void delete(final Customer customer) {
		  assert customer != null;
		  assert customer.getId() != 0;

		  Assert.isTrue(this.customerRepository.exists(customer.getId()));
		  
		  Customer principal;
		  principal = findOneByPrincipal();
		  Assert.isTrue(customer.equals(principal));
		  
		
		  
		  this.customerRepository.delete(customer);
		 }

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Customer a;
		a = this.customerRepository.findByUserAccountId(userAccount.getId());
		return a;

	}

	public Customer findOneByPrincipal() {
		Customer result;
		result = this.customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(result);
		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> customerities = userAccount.getAuthorities();
		Assert.notNull(customerities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);

		Assert.isTrue(customerities.contains(auth));
	}
	
	public void checkNotPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> customerities = userAccount.getAuthorities();
		Assert.notNull(customerities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);

		Assert.isTrue(!customerities.contains(auth));
	}
	
    
  //Reconstructor
  	public Customer reconstruct(Customer a, BindingResult binding){
  		Customer result;
  		
  		if(a.getId() == 0){
  			result = a;
  		}else{
  			result = customerRepository.findOne(a.getId());
  			result.setName(a.getName());
  			result.setEmail(a.getEmail());
  			validator.validate(result, binding);
  		}
  		return result;
  	}
  	
	
  	
}
