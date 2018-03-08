package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Pizzakaya

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
                "Pizza",
                listOf(Pizzakaya().modelDB())
        )
    }

    var createArgument: CategoryModelNew? = null
    override fun create(categoryModelNew: CategoryModelNew): HashMap<String, Long> {
        createArgument = categoryModelNew
        val hashMap = HashMap<String, Long>()
        hashMap.set("id", 1)
        return hashMap
    }

    var removeRestaurantArgument_id: Long? = null
    var removeRestaurantArgument_restaurantId: Long? = null
    override fun removeRestaurant(id: Long, restaurantId: Long) {
        removeRestaurantArgument_id = id
        removeRestaurantArgument_restaurantId = restaurantId
    }

    var destroyArgument: Long? = null
    override fun destroy(id: Long) {
        destroyArgument = id
    }
}