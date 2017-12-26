package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryModel

class RestaurantRepoStub : RestaurantRepo {
    override fun all(): List<RestaurantModelDB> {
        return listOf(
                RestaurantModelDB(1, "Pintokona", listOf(1L)),
                RestaurantModelDB(2, "Momodori", listOf(2L))
        )
    }

    override fun get(id: Long): RestaurantModel {
        return RestaurantModel(1, "Pintokona", listOf(CategoryModel(1L, "Sushi")))
    }

    var createArgument: RestaurantModelNew? = null
    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        createArgument = restaurantModelNew
        return 0L
    }
}