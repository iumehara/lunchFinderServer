package io.umehara.lunchFinderServer.restaurant

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantsController (val repo: RestaurantRepo) {
    @GetMapping
    fun index(): List<RestaurantModelDB> = repo.all()

    @GetMapping("{id}")
    fun show(@PathVariable id: Long): RestaurantModel = repo.get(id)

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody restaurantModelNew: RestaurantModelNew): Long = repo.create(restaurantModelNew)

    @PutMapping("{id}")
    fun update(
            @PathVariable id: Long,
            @RequestBody restaurantModelNew: RestaurantModelNew
    ) = repo.update(id, restaurantModelNew)
}
