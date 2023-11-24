package org.koffa.beercrudjwtdb.controllers;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.Beer;
import org.koffa.beercrudjwtdb.services.BeerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beers")
@RequiredArgsConstructor
public class BeerController {
    private final BeerService beerService;
    @GetMapping
    public ResponseEntity<Iterable<Beer>> findAll() {
        try {
            return ResponseEntity.ok(beerService.findAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<Beer> findBeersById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(beerService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            beerService.deleteById(id);
            return ResponseEntity.ok("Beer deleted: " + id);
        } catch (NoResultException e) {
            return ResponseEntity.ok("Beer deleted: 0");
        }
    }
    @PostMapping
    public ResponseEntity<Beer> save(@RequestBody Beer beer) {
        try {
            return ResponseEntity.ok(beerService.save(beer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<Beer> update(@RequestBody Beer beer) {
        try {
            return ResponseEntity.ok(beerService.update(beer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}