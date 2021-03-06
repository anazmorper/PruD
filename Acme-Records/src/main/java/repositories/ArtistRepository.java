package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import domain.Artist;
import domain.Record;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {


	
}
