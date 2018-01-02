package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantModelDB

data class CategoryModel(
        val id: Long = 0,
        val name: String = "",
        val restaurants: List<RestaurantModelDB> = emptyList()
)
