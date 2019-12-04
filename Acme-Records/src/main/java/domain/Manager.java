
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Atributos

	// Relationships

	private Collection<WorkProgramme> workProgrammes;
	private Collection<Artist> artists;

	@Valid
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	public Collection<WorkProgramme> getWorkProgrammes() {
		return workProgrammes;
	}

	public void setWorkProgrammes(Collection<WorkProgramme> workProgrammes) {
		this.workProgrammes = workProgrammes;
	}
	
	@Valid
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	public Collection<Artist> getArtists() {
		return artists;
	}

	public void setArtists(Collection<Artist> artists) {
		this.artists = artists;
	}

	// Metodos

}
