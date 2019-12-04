
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	//Relationships
	

	// Control check
	private Collection<Setter> setters;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	public Collection<Setter> getSetters() {
		return this.setters;
	}

	public void setSetters(final Collection<Setter> setters) {
		this.setters = setters;
	}
}
