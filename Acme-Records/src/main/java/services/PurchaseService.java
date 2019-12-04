package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Customer;
import domain.Purchase;
import repositories.PurchaseRepository;

@Service
@Transactional
public class PurchaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PurchaseRepository purchaseRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public PurchaseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Purchase create() {
		Purchase result;
		
		Customer c = this.customerService.findOneByPrincipal();
		this.customerService.checkPrincipal();
		
		result = new Purchase();
		result.setMoment(new Date());
		result.setCustomer(c);

		return result;
	}

	public Collection<Purchase> findPurchasesByCustomerLogged() {
		
		Customer c = this.customerService.findOneByPrincipal();
		customerService.checkPrincipal();
		
		Assert.notNull(c.getId());
		
		Collection<Purchase> result = c.getPurchases();
		
		Assert.notNull(result);
		return result;
	}

	public Purchase findOne(final int purchaseId) {
		Assert.isTrue(purchaseId != 0);

		Purchase result;

		result = this.purchaseRepository.findOne(purchaseId);
		Assert.notNull(result);

		return result;
	}
	
	public Purchase saveCreate(final Purchase p) {
		Assert.notNull(p);
		Customer c = this.customerService.findOneByPrincipal();
		this.customerService.checkPrincipal();
		
		
		p.setCustomer(c);
		Assert.notNull(p.getCreditCard());
		Assert.notNull(p.getRecord());
		Assert.notNull(p.getCustomer());
		
		Purchase result;
		
		if(p.getId() == 0){
			result = this.purchaseRepository.save(p);
		
			if(!c.getPurchases().contains(result)){
			c.getPurchases().add(p);
			this.customerService.save(c);
		}}else{
			p.setMoment(new Date());
			result = this.purchaseRepository.save(p);
		}
		
		return result;
	}
	
	//Other methods
	
	public Double[] avgMinMaxStPurchasesByCustomer(){
		return this.purchaseRepository.avgMinMaxStPurchasesByCustomer();
	}
	
}
