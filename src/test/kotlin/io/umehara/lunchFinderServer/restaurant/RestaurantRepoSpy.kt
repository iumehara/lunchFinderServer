package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryModelDB

class RestaurantRepoSpy : RestaurantRepo {
    override fun all(): List<RestaurantModelDB> {
        return listOf(
                RestaurantModelDB(1, "Pintokona", listOf(1L)),
                RestaurantModelDB(2, "Momodori", listOf(2L))
        )
    }

    override fun get(id: Long): RestaurantModel {
        return RestaurantModel(1, "Pintokona", listOf(CategoryModelDB(1L, "Sushi")))
    }

    var createArgument: RestaurantModelNew? = null
    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        createArgument = restaurantModelNew
        return 0L
    }

    var updateArgumentRestaurantModelNew: RestaurantModelNew? = null
    var updateArgumentId: Long? = null
    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        updateArgumentId = id
        updateArgumentRestaurantModelNew = restaurantModelNew
    }

    var addCategoryArgumentId: Long? = null
    var addCategoryArgumentCategoryId: Long? = null
    override fun addCategory(id: Long, categoryId: Long) {
        addCategoryArgumentId = id
        addCategoryArgumentCategoryId = categoryId
    }
}
