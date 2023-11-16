package org.koffa.beercrudjwtdb.repositories;

import org.koffa.beercrudjwtdb.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    @Query("SELECT DISTINCT b.brewery FROM Beer b")
    Iterable<String> getAllBreweries();

    Optional<Iterable<Beer>> findByType(String type);
}