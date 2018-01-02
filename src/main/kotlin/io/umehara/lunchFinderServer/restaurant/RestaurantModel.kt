package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryModelDB

data class RestaurantModel(
        var id: Long = 0,
        var name: String = "",
        var categories: List<CategoryModelDB> = ArrayList()
) {
    constructor(
            restaurantModelDB: RestaurantModelDB,
            categories: List<CategoryModelDB>
    ): this(restaurantModelDB.id, restaurantModelDB.name, categories)
}