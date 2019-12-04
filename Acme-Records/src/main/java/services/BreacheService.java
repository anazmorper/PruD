/*
 * BreacheService.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BreacheRepository;
import domain.Breache;

@Service
@Transactional
public class BreacheService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BreacheRepository	breacheRepository;
	@Autowired
	private AdministratorService	adminService;

	// Supporting services ----------------------------------------------------



	// Constructors -----------------------------------------------------------

	public BreacheService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Breache create() {
		Breache result;
		adminService.checkPrincipal();
		result = new Breache();

		return result;
	}

	public Collection<Breache> findAll() {
		Collection<Breache> result;
		
		Assert.notNull(this.breacheRepository);
		result = this.breacheRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Breache findOne(final int breacheId) {
		Breache result;

		result = this.breacheRepository.findOne(breacheId);

		return result;
	}

	public Breache save(final Breache breache) {
		Assert.notNull(breache);
		adminService.checkPrincipal();
		Assert.hasText(breache.getDescription());
		Assert.hasText(breache.getTitle());
		Breache result;

		result = this.breacheRepository.save(breache);

		return result;
	}


//	// Other business methods -------------------------------------------------
//
//	public Collection<Breache> findByTitle(String title) {
//		Collection<Breache> result;
//
//		result = this.breacheRepository.findByTitle(title);
//		Assert.notNull(result);
//
//		return result;
//	}

	

}
