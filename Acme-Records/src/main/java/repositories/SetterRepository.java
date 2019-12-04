/*
 * SetterRepository.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Setter;

@Repository
public interface SetterRepository extends JpaRepository<Setter, Integer> {

	// THE AVERAGE AND THE STANDARD DEVIATION OF THE NUMBER OF PUBLISHED SETTERS
		// PER RECORD

		@Query("select avg(1.0*(select count(n) from Setter n where n.finalMode=true and n member of c.setters)),stddev(1.0*(select count(n) from Setter n where n.finalMode = true and n member of c.setters)) from Record c")
		Double[] findAvgStddevOfTheNumOfSettersPublishedPerRecord();

		// THE RATIO OF PUBLISHED SETTERS VERSUS TOTAL NUMBER OF SETTERS

		@Query("select (select count(n) from Setter n where n.finalMode=true) *1.0 / count(r) from Setter r")
		Double findRatOfSetterPublished();

		// THE RATIO OF UNPUBLISHED SETTERS VERSUS TOTAL NUMBER OF SETTERS

		@Query("select (select count(n) from Setter n where n.finalMode=false) *1.0 / count(r) from Setter r")
		Double findRatOfSetterUnpublished();

}
