package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryModel

data class RestaurantModel(
        var id: Long = 0,
        var name: String = "",
        var categories: List<CategoryModel> = ArrayList()
) {
    constructor(
            restaurantModelDB: RestaurantModelDB,
            categories: List<CategoryModel>
    ): this(restaurantModelDB.id, restaurantModelDB.name, categories)
}