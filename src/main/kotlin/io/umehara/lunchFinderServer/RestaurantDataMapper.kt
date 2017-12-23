package io.umehara.lunchFinderServer

interface RestaurantDataMapper {
    fun all(): List<RestaurantModel>
    fun get(id: Long): RestaurantModel
    fun create(restaurantModelNew: RestaurantModelNew): Long
}