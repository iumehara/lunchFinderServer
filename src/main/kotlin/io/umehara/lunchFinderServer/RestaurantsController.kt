package io.umehara.lunchFinderServer

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantsController (val repo: RestaurantRepo) {
    @GetMapping
    fun index() = repo.all()

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody  restaurantModelNew: RestaurantModelNew) = repo.create(restaurantModelNew)
}