
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;


@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {
	
	private CreditCard creditCard;
	
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	//Relationships 

	private Collection<Purchase> purchases;
	
	@OneToMany(cascade={CascadeType.MERGE,CascadeType.REMOVE})
	public Collection<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Collection<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	
		
}
