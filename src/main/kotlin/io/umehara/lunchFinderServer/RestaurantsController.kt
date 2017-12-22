package io.umehara.lunchFinderServer

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("restaurants")
class RestaurantsController (val repo: RestaurantRepo) {
    @GetMapping
    fun index() = repo.all()
}