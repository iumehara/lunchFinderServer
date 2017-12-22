package io.umehara.lunchFinderServer

import org.springframework.stereotype.Repository

@Repository
class RestaurantRepoDB(val restaurantDataMapper: RestaurantDataMapper): RestaurantRepo {
    override fun all(): List<Restaurant> {
        return restaurantDataMapper.all()
    }
}