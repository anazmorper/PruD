/*
 * SetterService.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SetterRepository;
import domain.Actor;
import domain.Setter;
import domain.Record;
import domain.Administrator;

@Service
@Transactional
public class SetterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SetterRepository	setterRepository;
	@Autowired
	private RecordService	recordService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ActorService	actorService;
	
	// Supporting services ----------------------------------------------------



	// Constructors -----------------------------------------------------------

	public SetterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Setter create() {
		administratorService.checkPrincipal();
		
		Setter result = new Setter();
		
		String ticker = generateTicker();
		result.setTicker(ticker);
		result.setFinalMode(false);
		
		return result;
	}

	public Collection<Setter> findAll() {
		Collection<Setter> result;
		Assert.notNull(this.setterRepository);
		result = this.setterRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Collection<Setter> findByRecordAndAvailable(int recordId) {
		Collection<Setter> result = new ArrayList<Setter>();
		Collection<Setter> setters = new ArrayList<Setter>();
		Record record = recordService.findOne(recordId);
		setters = record.getSetters();
		
		if (null != setters && !setters.isEmpty()) {
			for (Setter a : setters) {
				if (a.isFinalMode() && !result.contains(a)) {
					result.add(a);
				}
			}
		}
		
		Assert.notNull(result);
		return result;
	}
	
	public Collection<Setter> findByPrincipal() {
		Collection<Setter> result = new ArrayList<Setter>();
		if ((!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
				&& (actorService.findByPrincipal() instanceof Administrator)){
		Collection<Setter> resultAux = new ArrayList<Setter>(administratorService.findOneByPrincipal().getSetters());
		if (null != resultAux && !resultAux.isEmpty()) {
			for (Setter p : resultAux){
				if(!result.contains(p)){
					result.add(p);
				}
			}
		}
		}
		Assert.notNull(result);
		return result;
	}


	public Setter findOne(final int setterId) {
		Setter result;
		result = this.setterRepository.findOne(setterId);
		return result;
	}

	public Setter saveCreate(final Setter setter, final Record record) {
		  Assert.notNull(setter);
		  Assert.notNull(this.administratorService.findOneByPrincipal());
		  Administrator principal = administratorService.findOneByPrincipal();
		  Assert.hasText(setter.getBody());
		  Setter result;
		  

		  if (setter.getId() == 0 ){
		   
		   result = this.setterRepository.save(setter);

		   if (!record.getSetters().contains(result)){
		    record.getSetters().add(result);
		    this.recordService.save2(record);
		   }
		   
		   if (!principal.getSetters().contains(result)){
			   principal.getSetters().add(result);
			    this.administratorService.save2(principal);
			   }
		  
		  }else{
		   
			  if (setter.isFinalMode()){
				  setter.setPublicationMoment(new Date());
			  }
			  
			   result = this.setterRepository.save(setter);

		  }
		  
		  return result;
		 }
	
	public Setter save(final Setter setter) {
		  Assert.notNull(setter);
		Assert.notNull(this.administratorService.findOneByPrincipal());
		  Assert.hasText(setter.getBody());
		  Setter result;		
		  
		  if (setter.isFinalMode()){
			  setter.setPublicationMoment(new Date());
		  }
		  
		   result = this.setterRepository.save(setter);

		  return result;
		 }

	public void delete(final Setter setter) {
		Assert.notNull(setter);
		Assert.notNull(this.administratorService.findOneByPrincipal());
		Assert.isTrue(!setter.isFinalMode());
		Assert.isTrue(this.findByPrincipal().contains(setter));
		Administrator principal = administratorService.findOneByPrincipal();
		Collection<Record> allRecords = new ArrayList<Record>();
		allRecords = recordService.findAll();
		for (Record h : allRecords){
			if(h.getSetters().contains(setter)){
				h.getSetters().remove(setter);
				recordService.save2(h);
				break;
			}
		}
		
		if(principal.getSetters().contains(setter)){
			principal.getSetters().remove(setter);
			administratorService.save2(principal);
		}
		
		Assert.isTrue(setter.getId() != 0);
		this.setterRepository.delete(setter);		
	}

	// Other business methods -------------------------------------------------

	private String generateTicker() {
		StringBuilder result;
		Date fechaActual = new Date();
		char [] charsNumericos = "0123456789".toCharArray();
//		char [] numeroParaPattern = "123".toCharArray();
		int charsNumericosLength = charsNumericos.length;
//		int numeroParaPatternLength = numeroParaPattern.length;
		Random random = new Random();

		// Numero entero entre 25 y 75
		int numeroPattern = random.nextInt(3-1+1) + 1;
		StringBuffer number = new StringBuffer();
		
		for (int i=0;i<numeroPattern;i++){
		   number.append(charsNumericos[random.nextInt(charsNumericosLength)]);
		}
		
		result = new StringBuilder();
		
		SimpleDateFormat formato = new SimpleDateFormat("yy-MM-dd");
		String fechaFormat = formato.format(fechaActual);
		String year = fechaFormat.split("-")[0];
		String month = fechaFormat.split("-")[1];
		String day = fechaFormat.split("-")[2];
		
		result.append(year + month + day + "#" + number);

		return result.toString();
	}
	

		// DASHBOARD PLANTILLA
		public Double[] findAvgStddevOfTheNumOfSettersPublishedPerRecord() {
			return setterRepository
					.findAvgStddevOfTheNumOfSettersPublishedPerRecord();
		}

		public Double findRatOfSetterPublished() {
			return setterRepository.findRatOfSetterPublished();
		}

		public Double findRatOfSetterUnpublished() {
			return setterRepository.findRatOfSetterUnpublished();
		}
	
	
	
}
