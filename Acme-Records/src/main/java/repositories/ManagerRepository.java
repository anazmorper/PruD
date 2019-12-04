
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Artist;
import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select a from Manager a where a.userAccount.id = ?1")
	Manager findByUserAccountId(int id);

	@Query("select a from Manager a where a.userAccount.id = ?1")
	Manager findOneByPrincipal(int id);

	@Query("select a from Manager m join m.workProgrammes w join w.records r join r.artist a where m.id= ?1")
	Collection<Artist> findArtistsByManager(int id);
	
	@Query("select a from Manager m join m.artists a where m.id= ?1")
	Collection<Artist> findArtistsByManagerSinRecord(int id);

}
