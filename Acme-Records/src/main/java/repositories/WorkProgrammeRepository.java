package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorkProgramme;

@Repository
public interface WorkProgrammeRepository extends JpaRepository<WorkProgramme, Integer>{
	
	@Query("select avg(m.workProgrammes.size), min(m.workProgrammes.size),max(m.workProgrammes.size),Stddev(m.workProgrammes.size) from Manager m")
	Double[] avgMinMaxStWorkProgrammeByManager();

}
