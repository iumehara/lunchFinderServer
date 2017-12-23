package io.umehara.lunchFinderServer

interface RestaurantRepo {
    fun all(): List<RestaurantModel>
    fun create(restaurantModelNew: RestaurantModelNew): Long
}