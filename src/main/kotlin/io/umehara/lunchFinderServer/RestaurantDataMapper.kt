package io.umehara.lunchFinderServer

interface RestaurantDataMapper {
    fun all(): List<Restaurant>
}