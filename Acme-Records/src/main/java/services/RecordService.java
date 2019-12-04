
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Attachment;
import domain.Customer;
import domain.Manager;
import domain.Purchase;
import domain.Record;
import domain.WorkProgramme;

@Service
@Transactional
public class RecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecordRepository		recordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private WorkProgrammeService	workProgrammeService;

	@Autowired
	private ArtistService			artistService;

	@Autowired
	private ManagerService			managerService;


	// Constructors -----------------------------------------------------------

	public RecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Record create() {
		final Record record = new Record();
		record.setTicker(this.generateTicker());
		return record;
	}

	public Record createWorkProgramme() {
		Assert.notNull(this.managerService.findOneByPrincipal());
		final Record record = new Record();
		record.setTicker(this.generateTicker());
		return record;
	}

	public Record findOne(final int recordId) {
		Assert.isTrue(recordId != 0);

		Record result;

		result = this.recordRepository.findOne(recordId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Record> findAll() {
		Collection<Record> result;
		result = this.recordRepository.findAll();
		return result;

	}

	public Record save(final Record record) {
		Assert.notNull(record);
		Assert.hasText(record.getTitle());
		Assert.hasText(record.getTicker());
		Assert.hasText(record.getCoverPhoto());
		Assert.notNull(record.getRetailPrice());
		Assert.hasText(record.getLyrics());
		Assert.notNull(record.getAttachments());
		Assert.notNull(record.isFinalMode());
		Assert.hasText(record.getType());

		Record result = null;

		result = this.recordRepository.save(record);

		return result;
	}

	public Record saveWorkProgramme(final Record record, final WorkProgramme workProgramme) {
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.notNull(record);
		Assert.notNull(workProgramme);
		Assert.hasText(record.getTitle());
		Assert.hasText(record.getTicker());
		Assert.hasText(record.getCoverPhoto());
		Assert.notNull(record.getRetailPrice());
		Assert.hasText(record.getLyrics());
		Assert.notNull(record.getAttachments());
		Assert.notNull(record.isFinalMode());
		Assert.hasText(record.getType());
	
		
		Record result = null;
		
		if(record.getId()!=0){
			Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
			Assert.isTrue(this.recordsByManager(this.managerService.findOneByPrincipal()).contains(record));
			result = this.recordRepository.save(record);
		}else{			
			result = this.recordRepository.save(record);
			workProgramme.getRecords().add(result);			
			this.workProgrammeService.save(workProgramme);
			if(result.getArtist() != null){
				result.getArtist().setRecord(result);
				this.artistService.save(result.getArtist());	
			}					
		}		
		return result;
	}

	public void delete(final Record record) {
		Assert.notNull(record);
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.isTrue(this.recordsByManager(this.managerService.findOneByPrincipal()).contains(record));
		Assert.isTrue(record.getId() != 0);

		this.recordRepository.delete(record);
	}

	public void deleteWorkProgramme(final Record record, final WorkProgramme workProgramme) {
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.notNull(record);
		Assert.notNull(workProgramme);
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
		Assert.isTrue(this.recordsByManager(this.managerService.findOneByPrincipal()).contains(record));
		Assert.isTrue(record.getId() != 0);
		Assert.isTrue(!record.isFinalMode());
		if (workProgramme.getRecords().contains(record)) {
			workProgramme.getRecords().remove(record);
			this.workProgrammeService.save(workProgramme);
		}
		if (!record.getPurchases().isEmpty()) {
			for (final Purchase p : record.getPurchases())
				p.setRecord(null);
			record.getPurchases().clear();
		}
		if (record.getArtist() != null) {
			record.getArtist().setRecord(null);
			record.setArtist(null);
		}

		this.recordRepository.delete(record);
	}
	
	//^\\d{2}-([A-Z]){4}-(\\d){4}$
	private String generateTicker(){
		StringBuilder result;
		Date fechaActual = new Date();
		char [] charsLetras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		char [] charsDigitos = "0123456789".toCharArray();
		int charsLetrasLength = charsLetras.length;
		int charsDigitosLength = charsDigitos.length;
		Random random = new Random();
		StringBuffer caracteresletra = new StringBuffer();
		StringBuffer caracteresnumero = new StringBuffer();
		for (int i=0;i<4;i++){
			caracteresletra.append(charsLetras[random.nextInt(charsLetrasLength)]);
			caracteresnumero.append(charsDigitos[random.nextInt(charsDigitosLength)]);
		}
		result = new StringBuilder();
		
		SimpleDateFormat mod1 = new SimpleDateFormat("yy");
		String fecha = mod1.format(fechaActual);
		
		result.append(fecha + "-" + caracteresletra + "-" + caracteresnumero);

		return result.toString();
	}
	// Other business methods -------------------------------------------------

	public Collection<Record> recordsFinalModeTrue() {
		Collection<Record> records;
		records = this.recordRepository.findRecordsFinalModeTrue();
		return records;
	}

	public Collection<Record> recordsFinalModeTrueAndVisibleWorkProgramme() {
		Collection<Record> records;
		records = this.recordsFinalModeTrue();
		return records;
	}

	public Collection<Record> findRecordByKey(final String keyword) {
		Assert.notNull(keyword);

		Collection<Record> resultTicker;
		Collection<Record> resultTitle;
		Collection<Record> resultLyrics;
		Collection<Record> resultTotal;

		resultTotal = new ArrayList<Record>();
		resultTitle = this.recordRepository.findRecByKeyOnTitle("%" + keyword + "%");
		resultTicker = this.recordRepository.findRecByKeyOnTicker("%" + keyword + "%");
		resultLyrics = this.recordRepository.findRecByKeyOnLyrics("%" + keyword + "%");

		resultTotal.addAll(resultTitle);
		resultTotal.addAll(resultTicker);
		resultTotal.addAll(resultLyrics);

		final Set<Record> set = new HashSet<>(resultTotal);
		resultTotal.clear();
		resultTotal.addAll(set);

		return resultTotal;
	}

	public Collection<Record> findRecordsAvailableToPurchase() { //Quitar las final mode=false y las ya compradas por customer

		final UserAccount us = LoginService.getPrincipal();
		final Customer principal = this.customerService.findByUserAccount(us);
		this.customerService.checkPrincipal();

		final Collection<Record> result = this.recordRepository.findRecordsFinalModeTrue();
		final Collection<Purchase> purchases = principal.getPurchases();
		final Collection<Record> aux = new HashSet<Record>();

		for (final Purchase p : purchases)
			aux.add(p.getRecord()); //Lista con los records que ha comprado

		result.removeAll(aux); //Lista final con las que puede comprar

		Assert.notNull(result);
		return result;
	}
	
	public Record save2(final Record record) {
		Assert.notNull(record);
		Assert.hasText(record.getTitle());
		Assert.hasText(record.getTicker());
		Assert.hasText(record.getCoverPhoto());
		Assert.notNull(record.getRetailPrice());
		Assert.hasText(record.getLyrics());
		Assert.notNull(record.getAttachments());
		Assert.notNull(record.isFinalMode());
		Assert.hasText(record.getType());

		Record result = null;

		result = this.recordRepository.save(record);

		return result;
	}

	public Double[] avgMinMaxStRecordByWorkProgramme() {
		return this.recordRepository.avgMinMaxStRecordByWorkProgramme();
	}

	public Double[] avgMinMaxStRecordPrice() {
		return this.recordRepository.avgMinMaxStRecordPrice();
	}

	public Collection<Record> findTop3RecordsByPurchase() {
		final List<Record> recordsOrderByPurchase = new ArrayList<Record>(this.recordRepository.findTopRecordsByPurchase());
		final Collection<Record> result = new ArrayList<Record>();

		if (recordsOrderByPurchase.size()>0){
		result.add(recordsOrderByPurchase.get(0));
		}
		if (recordsOrderByPurchase.size()>1){
			result.add(recordsOrderByPurchase.get(1));
		}
		if (recordsOrderByPurchase.size()>2){
			result.add(recordsOrderByPurchase.get(2));
		}

		return result;
	}

	public Record findTopRecord() {
		final List<Record> recordsOrderByPurchase = new ArrayList<Record>(this.recordRepository.findTopRecordsByPurchase());
		Record result = null;

		if (recordsOrderByPurchase.size()>1){
			result=recordsOrderByPurchase.get(0);
		}

		return result;
	}

	public Collection<Record> findTop3RecordsByMoney() {
		final List<Record> recordsOrderByMoney = new ArrayList<Record>(this.recordRepository.findTopRecordsByMoney());
		final Collection<Record> result = new ArrayList<Record>();

		if (recordsOrderByMoney.size()>0){
			result.add(recordsOrderByMoney.get(0));
		}
		if (recordsOrderByMoney.size()>1){
			result.add(recordsOrderByMoney.get(1));
		}
		if (recordsOrderByMoney.size()>2){
			result.add(recordsOrderByMoney.get(2));
		}

		return result;
	}

	public Collection<Record> recordsByManager(final Manager m) {
		final Collection<Record> res = new ArrayList<Record>();

		for (final WorkProgramme w : m.getWorkProgrammes())
			res.addAll(w.getRecords());

		return res;
	}

	public Collection<Record> findRecordsPubliclyWorkProgramme() {
		final Collection<Record> res = new ArrayList<Record>();
		final Collection<WorkProgramme> allWP = new ArrayList<WorkProgramme>(this.workProgrammeService.findAll());
		for (final WorkProgramme w : allWP)
			if (!w.getEndDate().after(new Date()))
				res.addAll(w.getRecords());
		return res;
	}

	public Record findRecordByArtistId(final Integer artistId) {
		Record result;
		Assert.notNull(artistId);

		result = this.recordRepository.findRecordByArtistId(artistId);

		return result;
	}
	
	public void updateFinalModeRecords(){
		Collection<WorkProgramme> workProgrammes = new ArrayList<WorkProgramme>(this.workProgrammeService.findAll());
		for(WorkProgramme w: workProgrammes){
			if(!w.getRecords().isEmpty()){
				if(w.getEndDate().after(new Date())){
					for(Record r: w.getRecords()){
						r.setFinalMode(false);
					}
				}else{
					for(Record r: w.getRecords()){
						r.setFinalMode(true);
					}
				}
			}
		}
	}
	
	public Boolean comprobarURLAttachment(Attachment a){
		Boolean res = false;
		if((a.getUrl().endsWith(".com") || a.getUrl().endsWith(".es")) 
				&& (a.getUrl().startsWith("https://www") || a.getUrl().startsWith("http://www"))
				&& (a.getUrl().split("\\.").equals(3) || a.getUrl().split("\\.").equals(4))){
			res = true;
		}
		return res;
	}

}
