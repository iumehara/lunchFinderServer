package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryFixture.Pizza
import io.umehara.lunchFinderServer.category.CategoryFixture.Spicy
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Moti
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Pizzakaya

class RestaurantRepoSpy : RestaurantRepo {
    override fun all(): List<RestaurantModelDB> {
        return listOf(
                Pizzakaya(categoryIds = listOf(Pizza().id)).modelDB(),
                Moti(categoryIds = listOf(Spicy().id)).modelDB()
        )
    }

    override fun get(id: Long): RestaurantModel {
        return Pizzakaya(categories = listOf(Pizza().modelDb())).model()
    }


    var createArgument: RestaurantModelNew? = null
    override fun create(restaurantModelNew: RestaurantModelNew): HashMap<String, Long> {
        createArgument = restaurantModelNew
        val hashMap = HashMap<String, Long>()
        hashMap["id"] = 1L
        return hashMap
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

    var removeCategoryArgumentId: Long? = null
    var removeCategoryArgumentCategoryId: Long? = null
    override fun removeCategory(id: Long, categoryId: Long) {
        removeCategoryArgumentId = id
        removeCategoryArgumentCategoryId = categoryId
    }

    var destroyArgument: Long? = null
    override fun destroy(id: Long) {
        destroyArgument = id
    }
}
