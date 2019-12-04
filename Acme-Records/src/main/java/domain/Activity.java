package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity {
	
	// Constructors -----------------------------------------------------------

		public Activity() {
			super();
		}


		// Attributes -------------------------------------------------------------

		private String	title;
		private String	description;
		private Date	startDate;
		private Date	endDate;
		private String status;
		
		
		//Getters & Setters
		
		@NotBlank
		@SafeHtml
		public String getTitle() {
			return this.title;
		}

		public void setTitle(final String title) {
			this.title = title;
		}
		
		@NotBlank
		@SafeHtml
		public String getDescription() {
			return this.description;
		}

		public void setDescription(final String description) {
			this.description = description;
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
		
		@Pattern(regexp = "^TODO|ONGOING|FINISHED$")
		@NotBlank
		@SafeHtml
		public String getStatus() {
			return this.status;
		}

		public void setStatus(final String status) {
			this.status = status;
		}


}
