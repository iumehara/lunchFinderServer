package io.umehara.lunchFinderServer

interface RestaurantDataMapper {
    fun all(): List<RestaurantModel>
    fun create(restaurantModelNew: RestaurantModelNew): Long
}