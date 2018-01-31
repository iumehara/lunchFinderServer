package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryModelDB

data class RestaurantModel(
        val id: Long = 0,
        val name: String = "",
        val nameJp: String = "",
        val website: String? = null,
        val geoLocation: GeoLocation? = null,
        val categories: List<CategoryModelDB> = ArrayList()
) {
    constructor(
            restaurantModelDB: RestaurantModelDB,
            categories: List<CategoryModelDB>
    ): this(
            restaurantModelDB.id,
            restaurantModelDB.name,
            restaurantModelDB.nameJp,
            restaurantModelDB.website,
            restaurantModelDB.geoLocation,
            categories
    )
}