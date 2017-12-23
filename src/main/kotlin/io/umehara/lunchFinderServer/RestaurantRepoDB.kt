package io.umehara.lunchFinderServer

import org.springframework.stereotype.Repository

@Repository
class RestaurantRepoDB(val restaurantDataMapper: RestaurantDataMapper): RestaurantRepo {
    override fun all(): List<RestaurantModel> {
        return restaurantDataMapper.all()
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        return restaurantDataMapper.create(restaurantModelNew)
    }
}