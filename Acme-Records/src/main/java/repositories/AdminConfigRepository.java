
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AdminConfig;

@Repository
public interface AdminConfigRepository extends JpaRepository<AdminConfig, Integer> {

	@Query("select ac.defaultCountryCode from AdminConfig ac")
	public String getDefaultCountryCode();

	@Query("select ac.banner from AdminConfig ac")
	public String getSystemBanner();

	@Query("select ac.welcomeMessageEnglish from AdminConfig ac")
	public String getWelcomeMessageEnglish();

	@Query("select ac.welcomeMessageSpanish from AdminConfig ac")
	public String getWelcomeMessageSpanish();
	
	@Query("select t from AdminConfig s join s.creditCardNames t")
	public Collection<String> getCreditCardNames();

}
