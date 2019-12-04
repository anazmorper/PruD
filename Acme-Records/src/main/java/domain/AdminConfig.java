
package domain;



import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class AdminConfig extends DomainEntity {
	private String				defaultCountryCode;
	private String				welcomeMessageEnglish;
	private String				welcomeMessageSpanish;
	private String				banner;
	private int 				maxNumberFinder;
	private Collection<String>	defaultTopicsEnglish;
	private Collection<String>	defaultTopicsSpanish;
	private Collection<String>  creditCardNames;
	private Collection<String> 	status;
	private Collection<String> 	types;
	
	@NotBlank
	@Pattern(regexp = "^[+][1-9]\\d{0,2}$")
	public String getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final String defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
	}
	
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}
	@SafeHtml
	public String getWelcomeMessageEnglish() {
		return this.welcomeMessageEnglish;
	}

	public void setWelcomeMessageEnglish(final String welcomeMessageEnglish) {
		this.welcomeMessageEnglish = welcomeMessageEnglish;
	}
	@SafeHtml
	public String getWelcomeMessageSpanish() {
		return this.welcomeMessageSpanish;
	}

	public void setWelcomeMessageSpanish(final String welcomeMessageSpanish) {
		this.welcomeMessageSpanish = welcomeMessageSpanish;
	}

	@NotNull
	@Range(min = 1, max = 100)
	public Integer getMaxNumberFinder() {
		return maxNumberFinder;
	}

	public void setMaxNumberFinder(Integer maxNumberFinder) {
		this.maxNumberFinder = maxNumberFinder;
	}
	
	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getDefaultTopicsEnglish() {
		return this.defaultTopicsEnglish;
	}

	public void setDefaultTopicsEnglish(final Collection<String> defaultTopicsEnglish) {
		this.defaultTopicsEnglish = defaultTopicsEnglish;
	}
	
	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getDefaultTopicsSpanish() {
		return this.defaultTopicsSpanish;
	}

	public void setDefaultTopicsSpanish(final Collection<String> defaultTopicsSpanish) {
		this.defaultTopicsSpanish = defaultTopicsSpanish;
	}
	
	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getCreditCardNames() {
		return this.creditCardNames;
	}

	public void setCreditCardNames(final Collection<String> creditCardNames) {
		this.creditCardNames = creditCardNames;
	}
	
	@ElementCollection
	public Collection<String> getStatus() {
		return this.status;
	}

	public void setStatus(final Collection<String> status) {
		this.status = status;
	}
	
	@ElementCollection
	public Collection<String> getTypes() {
		return this.types;
	}

	public void setTypes(final Collection<String> types) {
		this.types = types;
	}
	
}
