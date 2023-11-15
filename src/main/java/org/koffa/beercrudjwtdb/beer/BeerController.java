package org.koffa.beercrudjwtdb.beer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/beers")
@RequiredArgsConstructor
public class BeerController {
    private final BeerService beerService;
    @GetMapping
    public Iterable<Beer> findAll() {
        return beerService.findAll();
    }
    @GetMapping("/breweries")
    public Iterable<String> getAllBreweries() {
        return beerService.getAllBreweries();
    }
    @GetMapping("{id}")
    public Beer findBeersByType(@PathVariable Long id) {
        return beerService.findById(id);
    }
    @PostMapping
    public Beer save(@RequestBody Beer beer) {
        return beerService.save(beer);
    }
}