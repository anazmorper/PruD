
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	// Search keyword ticker
	@Query("select r from Record r where r.ticker like ?1 and r.finalMode=true")
	public Collection<Record> findRecByKeyOnTicker(String keyword);

	// Search keyword title
	@Query("select r from Record r where r.title like ?1 and r.finalMode=true")
	public Collection<Record> findRecByKeyOnTitle(String keyword);

	// //Search keyword lyrics
	@Query("select r from Record r where r.lyrics like ?1 and r.finalMode=true")
	public Collection<Record> findRecByKeyOnLyrics(String keyword);

	//Search record final mode
	@Query("select r from Record r where r.finalMode = true")
	public Collection<Record> findRecordsFinalModeTrue();

	@Query("select avg(w.records.size), min(w.records.size),max(w.records.size),Stddev(w.records.size) from WorkProgramme w")
	Double[] avgMinMaxStRecordByWorkProgramme();

	@Query("select avg(r.retailPrice), min(r.retailPrice),max(r.retailPrice),Stddev(r.retailPrice) from Record r")
	Double[] avgMinMaxStRecordPrice();

	@Query("select r from Record r where r.purchases.size!=0 order by r.purchases.size desc")
	Collection<Record> findTopRecordsByPurchase();

	@Query("select r from Record r where r.purchases.size!=0 order by r.retailPrice*r.purchases.size desc")
	Collection<Record> findTopRecordsByMoney();

	@Query("select r from Record r join r.artist a where a.id =?1")
	public Record findRecordByArtistId(Integer artistId);
}
