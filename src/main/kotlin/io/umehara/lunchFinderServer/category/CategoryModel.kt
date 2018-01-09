package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantModel

data class CategoryModel(
        val id: Long = 0,
        val name: String = "",
        val restaurants: List<RestaurantModel> = emptyList()
)
