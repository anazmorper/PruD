/*
 * Certification.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Setter extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Setter() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	picture;
	private String	body;
	private Date publicationMoment;
	private String ticker;
	private boolean finalMode;
	private Integer tipoColor;


	@URL
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	@SafeHtml
	@Size(min = 1, max = 251)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getPublicationMoment() {
		return publicationMoment;
	}

	public void setPublicationMoment(Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}
	
	@NotBlank
	@SafeHtml
	@Pattern(regexp = "^(\\d){2}(\\d){2}(\\d){2}#(\\d){1,3}$")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}


	public boolean isFinalMode() {
		return finalMode;
	}

	public void setFinalMode(boolean finalMode) {
		this.finalMode = finalMode;
	}
	
	@NotNull
	public Integer getTipoColor(){

		Integer tipoColor = 0;

		Date fechaActual = new Date();
		long startTime = new Date().getTime();
		if (null != this.publicationMoment){
			startTime = this.publicationMoment.getTime();
		}
	    long endTime = fechaActual.getTime();
	    long diffTime = endTime - startTime;
	    long diffDays = diffTime / (1000 * 60 * 60 * 24);
	    int dias = (int)diffDays;
	    
	    if(dias<30){
	    	tipoColor = 1;
	    }else if(dias<60 && dias>=30){
	    	tipoColor = 2;
	    }else{
	    	tipoColor = 3;
	    }
	    this.tipoColor = tipoColor;
	    return this.tipoColor;
	}

	

	public void setTipoColor(Integer tipoColor){
		this.tipoColor = tipoColor;
	}

	//Relationships
	

	

}
