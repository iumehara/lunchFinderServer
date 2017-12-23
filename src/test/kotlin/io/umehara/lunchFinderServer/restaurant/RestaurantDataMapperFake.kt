package io.umehara.lunchFinderServer.restaurant

class RestaurantDataMapperFake: RestaurantDataMapper {
    private var restaurants = mutableListOf<RestaurantModel>()

    fun setSeedRestaurants(seedRestaurants: List<RestaurantModel>) {
        restaurants.addAll(seedRestaurants)
    }

    override fun all(): List<RestaurantModel> {
        return restaurants
    }

    override fun get(id: Long): RestaurantModel {
        val filteredRestaurants = restaurants.filter { it.id == id }
        return filteredRestaurants[0]
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        val id = restaurants.size + 1L
        val newRestaurant = RestaurantModel(id, restaurantModelNew.name)
        restaurants.add(newRestaurant)
        return id
    }
}