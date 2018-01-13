package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryFixture.Pizza
import io.umehara.lunchFinderServer.category.CategoryFixture.Spicy
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Moti
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Pizzakaya

class RestaurantRepoSpy : RestaurantRepo {
    override fun all(): List<RestaurantModelDB> {
        return listOf(
                Pizzakaya(categories = listOf(Pizza().modelDb())).modelDB(),
                Moti(categories = listOf(Spicy().modelDb())).modelDB()
        )
    }


    override fun get(id: Long): RestaurantModel {
        return Pizzakaya(categories = listOf(Pizza().modelDb())).model()
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


    var destroyArgument: Long? = null
    override fun destroy(id: Long) {
        destroyArgument = id
    }
}
