package io.umehara.lunchFinderServer.restaurant

interface RestaurantDataMapper {
    fun all(): List<RestaurantModelDB>
    fun allByCategoryId(categoryId: Long): List<RestaurantModelDB>
    fun get(id: Long): RestaurantModelDB
    fun create(restaurantModelNew: RestaurantModelNew): Long
    fun update(id: Long, restaurantModelNew: RestaurantModelNew)
}