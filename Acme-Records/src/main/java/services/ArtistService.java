
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ArtistRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Artist;
import domain.Customer;
import domain.Manager;
import domain.Purchase;
import domain.Record;

@Service
@Transactional
public class ArtistService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ArtistRepository	artistRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RecordService		recordService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ManagerService		managerService;


	// Constructors -----------------------------------------------------------

	public ArtistService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Artist create() {
		final Artist artist = new Artist();
		return artist;
	}

	public Artist findOne(final int artistId) {
		Assert.isTrue(artistId != 0);

		Artist result;

		result = this.artistRepository.findOne(artistId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Artist> findAll() {
		Collection<Artist> result;
		result = this.artistRepository.findAll();
		return result;

	}

	// Other business methods -------------------------------------------------

	public Collection<Artist> findbyrecordFinalModeTrue() {
		final Collection<Artist> result = new ArrayList<Artist>();
		final Collection<Record> records = this.recordService.recordsFinalModeTrue();

		for (final Record r : records)
			result.add(r.getArtist());
		return result;
	}

	public Collection<Artist> artistByActor() {
		Collection<Purchase> purchases = new ArrayList<Purchase>();
		final Collection<Record> records = new ArrayList<Record>();
		Collection<Artist> artists = new ArrayList<Artist>();
		//			Collection<WorkPrograme> programmes = new ArrayList<WorkPrograme>();

		final UserAccount u = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccount(u);

		if (a instanceof Customer) {
			final Customer c = this.customerService.findOneByPrincipal();
			this.customerService.checkPrincipal();
			Assert.notNull(c.getId());

			purchases = c.getPurchases();

			for (final Purchase p : purchases)
				records.add(p.getRecord());

			for (final Record r : records)
				artists.add(r.getArtist());

		}

		if (a instanceof Manager) {
			final Manager m = this.managerService.findOneByPrincipal();
			this.managerService.checkPrincipal();
			Assert.notNull(m.getId());

			artists.addAll(this.managerService.getArtistsByManagerSinRecord(m.getId()));

			//				
			//				programmes = m.getProgrammes();
			//				
			//				for(WorkProgramme w:programmes){
			//					records.add(w.getRecord());
			//				}
			//				
			//				for(Record r:records){
			//					artists.add(r.getArtist());
		}

		return artists;
	}

	public Collection<Record> recordsByActor() {
		Collection<Purchase> purchases = new ArrayList<Purchase>();
		final Collection<Record> records = new ArrayList<Record>();

		//			Collection<WorkPrograme> programmes = new ArrayList<WorkPrograme>();

		final UserAccount u = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccount(u);

		if (a instanceof Customer) {
			final Customer c = this.customerService.findOneByPrincipal();
			this.customerService.checkPrincipal();
			Assert.notNull(c.getId());

			purchases = c.getPurchases();

			for (final Purchase p : purchases)
				records.add(p.getRecord());

			//				if (a instanceof Manager){
			//					Manager m = this.managerService.findOneByPrincipal();
			//					managerService.checkPrincipal();
			//					Assert.notNull(m.getId());
			//					
			//					programmes = m.getProgrammes();
			//					
			//					for(WorkProgramme w:programmes){
			//						records.add(w.getRecord());
			//					}

		}

		return records;
	}

	public void delete(final Artist artist) {
		Actor principal;
		final Record record;

		principal = this.actorService.findByPrincipal();

		Assert.isInstanceOf(Manager.class, principal);
		Assert.notNull(artist);
		Assert.isTrue(artist.getId() != 0);

	
		
		/*
		 * Si{ Hay record que tengan ese artista -> borrarlo del record
		 * 
		 * }
		 * 
		 * borrar el artista
		 */

		record = this.recordService.findRecordByArtistId(artist.getId());
		

		if (record != null)
			record.setArtist(null);

		
		if(this.managerService.findOneByPrincipal().getArtists().contains(artist)){
			this.managerService.findOneByPrincipal().getArtists().remove(artist);
			this.managerService.save(this.managerService.findOneByPrincipal());
		}
		this.artistRepository.delete(artist);
	}
	public Artist save(final Artist artist) {
		Assert.notNull(artist);
		final Manager m = this.managerService.findOneByPrincipal();

		Assert.isInstanceOf(Manager.class, m);

		Artist result;
		
		if(artist.getId() == 0){
			result = this.artistRepository.save(artist);
			this.managerService.findOneByPrincipal().getArtists().add(result);
			this.managerService.save(this.managerService.findOneByPrincipal());
		}else{
			result = this.artistRepository.save(artist);
		}

		return result;
	}

	public Collection<Artist> freeArtists() {
		final Collection<Artist> res = new ArrayList<Artist>();
		final Collection<Artist> allArtists = new ArrayList<Artist>(this.managerService.getArtistsByManagerSinRecord(this.managerService.findOneByPrincipal().getId()));
		for (final Artist a : allArtists)
			if (a.getRecord() == null)
				res.add(a);
		return res;
	}
}