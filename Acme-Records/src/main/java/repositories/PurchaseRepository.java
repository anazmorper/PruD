package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	@Query("select avg(c.purchases.size), min(c.purchases.size),max(c.purchases.size),Stddev(c.purchases.size) from Customer c")
	Double[] avgMinMaxStPurchasesByCustomer();

}
