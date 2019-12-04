
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Record extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Record() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String					title;
	private String					ticker;
	private String					coverPhoto;
	private Double					retailPrice;
	private String					lyrics;
	private Collection<Attachment>	attachments;
	private boolean					finalMode;
	private String					type;

	// Relationships

	private Collection<Purchase>	purchases;
	private Artist					artist;


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
	@Pattern(regexp = "^\\d{2}-([A-Z]){4}-(\\d){4}$")
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getCoverPhoto() {
		return this.coverPhoto;
	}
	public void setCoverPhoto(final String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	@NotNull
	@Min(value = 1)
	public Double getRetailPrice() {
		return this.retailPrice;
	}
	public void setRetailPrice(final Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	@NotBlank
	@SafeHtml
	public String getLyrics() {
		return this.lyrics;
	}
	public void setLyrics(final String lyrics) {
		this.lyrics = lyrics;
	}

	@NotNull
	@ElementCollection
	@Valid
	public Collection<Attachment> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final Collection<Attachment> attachments) {
		this.attachments = attachments;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}
	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	@Pattern(regexp = "^VINYL|VIDEO|SINGLE$")
	@NotBlank
	@SafeHtml
	public String getType() {
		return this.type;
	}
	public void setType(final String type) {
		this.type = type;
	}

	@OneToMany(cascade = {
		CascadeType.MERGE
	})
	//Si se borra el record, no deberian borrarse sus compras
	public Collection<Purchase> getPurchases() {
		return this.purchases;
	}
	public void setPurchases(final Collection<Purchase> purchases) {
		this.purchases = purchases;
	}

	@OneToOne(optional = true)
	public Artist getArtist() {
		return this.artist;
	}
	public void setArtist(final Artist artist) {
		this.artist = artist;
	}

}
