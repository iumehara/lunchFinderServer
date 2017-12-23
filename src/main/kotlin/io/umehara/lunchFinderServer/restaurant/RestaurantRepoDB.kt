package io.umehara.lunchFinderServer.restaurant

import org.springframework.stereotype.Repository

@Repository
class RestaurantRepoDB(val restaurantDataMapper: RestaurantDataMapper): RestaurantRepo {
    override fun all() = restaurantDataMapper.all()

    override fun get(id: Long) = restaurantDataMapper.get(id)


    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        return restaurantDataMapper.create(restaurantModelNew)
    }
}