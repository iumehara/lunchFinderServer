package io.umehara.lunchFinderServer.restaurant

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("categories/{categoryId}/restaurants")
class CategoryRestaurantsController(val repo: RestaurantRepo) {
    @GetMapping
    fun index(@PathVariable categoryId: Long): List<RestaurantModelDB> {
        return repo.where(categoryId)
    }

    @GetMapping("full")
    fun indexFull(@PathVariable categoryId: Long): List<RestaurantModel> {
        return repo.whereFull(categoryId)
    }

    @DeleteMapping("{id}")
    fun remove(@PathVariable categoryId: Long, @PathVariable id: Long) {
        return repo.removeCategory(id, categoryId)
    }
}
