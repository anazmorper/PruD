
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Artist extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Artist() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	photo;
	private String	biography;
	private String	homePage;

	// Relationships

	private Record	record;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@SafeHtml
	public String getBiography() {
		return this.biography;
	}
	public void setBiography(final String biography) {
		this.biography = biography;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getHomePage() {
		return this.homePage;
	}
	public void setHomePage(final String homePage) {
		this.homePage = homePage;
	}

	@ManyToOne
	public Record getRecord() {
		return this.record;
	}
	public void setRecord(final Record record) {
		this.record = record;
	}
	
	public String toString() {
	    return name;
	  }

}
