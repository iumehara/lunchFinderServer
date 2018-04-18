package io.umehara.lunchFinderServer.restaurant

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("categories/{categoryId}/restaurants")
class CategoryRestaurantsController(val repo: RestaurantRepo) {

    @GetMapping
    fun index(@PathVariable categoryId: Long): List<RestaurantModelDB> {
        return repo.where(categoryId)
    }
}
