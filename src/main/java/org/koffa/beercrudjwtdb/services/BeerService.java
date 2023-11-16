package org.koffa.beercrudjwtdb.services;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.Beer;
import org.koffa.beercrudjwtdb.repositories.BeerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeerService {
    private final BeerRepository beerRepository;

    public Beer save(Beer beer) {
        return beerRepository.save(beer);
    }
    public Beer findById(Long id) {
        return beerRepository.findById(id).orElseThrow(() -> new NoResultException("No beer found for id " + id));
    }
    public void deleteById(Long id) {
        beerRepository.deleteById(id);
    }
    public Beer update(Long id, Beer beer) {
        Beer beerToUpdate = findById(id);
        beerToUpdate.setName(beer.getName());
        beerToUpdate.setType(beer.getType());
        beerToUpdate.setBrewery(beer.getBrewery());
        return beerRepository.save(beerToUpdate);
    }
    public Iterable<Beer> findAll() {
        return beerRepository.findAll();
    }
    public Iterable<String> getAllBreweries() {
        return beerRepository.getAllBreweries();
    }
    public Iterable<Beer> findBeersByType(String type) {
        return beerRepository.findByType(type).orElseThrow(() -> new NoResultException("No beer found for type " + type));
    }
}