package io.umehara.lunchFinderServer.restaurant

interface RestaurantRepo {
    fun all(): List<RestaurantModel>
    fun get(id: Long): RestaurantModel
    fun create(restaurantModelNew: RestaurantModelNew): Long
}