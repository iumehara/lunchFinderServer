package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantModel

class CategoryRepoSpy : CategoryRepo {
    override fun all(): List<CategoryModelDB> {
        return listOf(
                CategoryModelDB(1, "Curry"),
                CategoryModelDB(2, "Sushi")
        )
    }

    override fun get(id: Long): CategoryModel {
        return CategoryModel(
                1,
                "Curry",
                listOf(RestaurantModel(1L, "Green Asia", "グリーンアジア", listOf(CategoryModelDB(1, "Curry"), CategoryModelDB(3, "Spicy"))))
        )
    }

    var createArgument: CategoryModelNew? = null
    override fun create(categoryModelNew: CategoryModelNew): Long {
        createArgument = categoryModelNew
        return 1L
    }

    var destroyArgument: Long? = null
    override fun destroy(id: Long) {
        destroyArgument = id
    }
}