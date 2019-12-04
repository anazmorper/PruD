
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WorkProgrammeRepository;
import domain.Activity;
import domain.WorkProgramme;

@Service
@Transactional
public class WorkProgrammeService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private WorkProgrammeRepository			workProgrammeRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ManagerService				managerService;

	// Constructors -----------------------------------------------------------
	public WorkProgrammeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public WorkProgramme create() {
		Assert.notNull(managerService.findOneByPrincipal());
		return new WorkProgramme();
	}
	
	public WorkProgramme findOne(final int workProgrammeId) {
		Assert.isTrue(workProgrammeId != 0);

		WorkProgramme result;

		result = this.workProgrammeRepository.findOne(workProgrammeId);
		Assert.notNull(result);

		return result;
	}

	public Collection<WorkProgramme> findAll() {
		Collection<WorkProgramme> result;
		result = this.workProgrammeRepository.findAll();
		return result;
	}

	public WorkProgramme save(final WorkProgramme workProgramme) {
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.notNull(workProgramme);
		Assert.hasText(workProgramme.getCodeName());
		Assert.notNull(workProgramme.getStartDate());
		Assert.notNull(workProgramme.getEndDate());
		Assert.isTrue(workProgramme.getStartDate().after(new Date()));
		Assert.isTrue(workProgramme.getEndDate().after(new Date()));
		Assert.isTrue(workProgramme.getEndDate().after(workProgramme.getStartDate()));
		
		WorkProgramme result;

		if(workProgramme.getId() == 0){
			result = this.workProgrammeRepository.save(workProgramme);
			this.managerService.findOneByPrincipal().getWorkProgrammes().add(result);
			this.managerService.save(this.managerService.findOneByPrincipal());
		}else{
			Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
			result = this.workProgrammeRepository.save(workProgramme);
		}

		
		return result;
	}
	public void delete(final WorkProgramme workProgramme) {
		Assert.notNull(workProgramme);
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));	
		Assert.isTrue(workProgramme.getId() != 0);
		
		workProgramme.getRecords().clear();
		workProgramme.getActivities().clear();
		
		if(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme)){
			this.managerService.findOneByPrincipal().getWorkProgrammes().remove(workProgramme);
			this.managerService.save(this.managerService.findOneByPrincipal());
		}
			
		
		this.workProgrammeRepository.delete(workProgramme);
	}
	
	//Other methods
	
	public Double[] avgMinMaxStWorkProgrammeByManager(){
		return this.workProgrammeRepository.avgMinMaxStWorkProgrammeByManager();
	}
	
	public WorkProgramme WorkProgrammeByActivity(Activity a){
		WorkProgramme result = null;
		Collection<WorkProgramme> res = new ArrayList<WorkProgramme>(findAll());
		for(WorkProgramme w: res){
			if (w.getActivities().contains(a)){
				result = w;
				break;
			}
		}
		Assert.notNull(result);
		return result;
	}
}
