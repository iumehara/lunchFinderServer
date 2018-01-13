package io.umehara.lunchFinderServer.restaurant

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantsController (val repo: RestaurantRepo) {
    @GetMapping
    fun index(): List<RestaurantModelDB> {
        return repo.all()
    }

    @GetMapping("{id}")
    fun show(@PathVariable id: Long): RestaurantModel {
        return repo.get(id)
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody restaurantModelNew: RestaurantModelNew): Long {
        return repo.create(restaurantModelNew)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody restaurantModelNew: RestaurantModelNew) {
        repo.update(id, restaurantModelNew)
    }

    @PutMapping("{id}/categories/{categoryId}")
    fun addCategory(@PathVariable id: Long, @PathVariable categoryId: Long) {
        repo.addCategory(id, categoryId)
    }

    @DeleteMapping("{id}")
    fun destroy(@PathVariable id: Long) {
        repo.destroy(id)
    }
}
