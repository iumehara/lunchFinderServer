package io.umehara.lunchFinderServer.restaurant

interface RestaurantRepo {
    fun all(): List<RestaurantModelDB>
    fun get(id: Long): RestaurantModel
    fun create(restaurantModelNew: RestaurantModelNew): Long
    fun update(id: Long, restaurantModelNew: RestaurantModelNew)
    fun addCategory(id: Long, categoryId: Long)
}