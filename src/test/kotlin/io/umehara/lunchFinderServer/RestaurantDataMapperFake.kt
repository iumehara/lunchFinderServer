package io.umehara.lunchFinderServer

class RestaurantDataMapperFake: RestaurantDataMapper {
    private var restaurants = mutableListOf<RestaurantModel>()

    fun setSeedRestaurants(seedRestaurants: List<RestaurantModel>) {
        restaurants.addAll(seedRestaurants)
    }

    override fun all(): List<RestaurantModel> {
        return restaurants
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        val id = restaurants.size + 1L
        val newRestaurant = RestaurantModel(id, restaurantModelNew.name)
        restaurants.add(newRestaurant)
        return id
    }
}