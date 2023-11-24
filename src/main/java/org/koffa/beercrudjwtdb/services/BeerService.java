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
    public void deleteById(Long id) throws NoResultException {
        if(beerRepository.existsById(id)) beerRepository.deleteById(id);
        else throw new NoResultException("No beer found for id " + id);
    }
    public Beer update(Beer beer) {
        if(!beerRepository.existsById(beer.getId())) throw new NoResultException("No beer found for id " + beer.getId());
        return beerRepository.save(beer);
    }
    public Iterable<Beer> findAll() {
        return beerRepository.findAll();
    }
}