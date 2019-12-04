
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Manager;
import domain.WorkProgramme;

@Service
@Transactional
public class ActivityService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActivityRepository			activityRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ManagerService				managerService;
	
	@Autowired
	private WorkProgrammeService				workProgrammeService;

	// Constructors -----------------------------------------------------------
	public ActivityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Activity create() {
		Assert.notNull(managerService.findOneByPrincipal());
		return new Activity();
	}
	
	public Activity findOne(final int activityId) {
		Assert.isTrue(activityId != 0);

		Activity result;

		result = this.activityRepository.findOne(activityId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Activity> findAll() {
		Collection<Activity> result;
		result = this.activityRepository.findAll();
		return result;
	}

	public Activity save(final Activity activity, final WorkProgramme workProgramme) {
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.notNull(activity);
		Assert.notNull(workProgramme);
		Assert.hasText(activity.getDescription());
		Assert.hasText(activity.getStatus());
		Assert.hasText(activity.getTitle());
		Assert.notNull(activity.getStartDate());
		Assert.notNull(activity.getEndDate());
		Assert.isTrue(activity.getStartDate().after(new Date()));
		Assert.isTrue(activity.getEndDate().after(new Date()));
		Assert.isTrue(activity.getStartDate().after(workProgramme.getStartDate()));
		Assert.isTrue(activity.getStartDate().before(workProgramme.getEndDate()));
		Assert.isTrue(activity.getEndDate().after(workProgramme.getStartDate()));
		Assert.isTrue(activity.getEndDate().before(workProgramme.getEndDate()));
		Assert.isTrue(activity.getEndDate().after(activity.getStartDate()));
		
		Activity result;

		if(activity.getId() != 0){
			Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
			Assert.isTrue(this.activitiesByManager(this.managerService.findOneByPrincipal()).contains(activity));
			result = this.activityRepository.save(activity);
		}else{
			result = this.activityRepository.save(activity);
			workProgramme.getActivities().add(result);
			this.workProgrammeService.save(workProgramme);			
		}		
		return result;

	}
	public void delete(final Activity activity, final WorkProgramme workProgramme) {
		Assert.notNull(this.managerService.findOneByPrincipal());
		Assert.notNull(activity);
		Assert.notNull(workProgramme);
		Assert.isTrue(this.managerService.findOneByPrincipal().getWorkProgrammes().contains(workProgramme));
		Assert.isTrue(this.activitiesByManager(this.managerService.findOneByPrincipal()).contains(activity));
		Assert.isTrue(activity.getId() != 0);
		
		workProgramme.getActivities().remove(activity);
		this.workProgrammeService.save(workProgramme);
		this.activityRepository.delete(activity);
	}
	
	public Collection<Activity> activitiesByManager(Manager m){
		Collection<Activity> res = new ArrayList<Activity>();
		
		for(WorkProgramme w: m.getWorkProgrammes()){
			res.addAll(w.getActivities());
		}
		
		return res;
	}
}
