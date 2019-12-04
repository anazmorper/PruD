
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class WorkProgramme extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public WorkProgramme() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String					codeName;
	private Date					startDate;
	private Date					endDate;

	// Relationships

	private Collection<Activity>	activities;
	private Collection<Record>		records;


	@Valid
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	public Collection<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(final Collection<Activity> activities) {
		this.activities = activities;
	}

	@Valid
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	public Collection<Record> getRecords() {
		return this.records;
	}

	public void setRecords(final Collection<Record> records) {
		this.records = records;
	}

	//Getters & Setters

	@NotBlank
	@SafeHtml
	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(final String codeName) {
		this.codeName = codeName;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
