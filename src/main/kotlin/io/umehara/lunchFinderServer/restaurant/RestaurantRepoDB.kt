package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryDataMapper
import org.springframework.stereotype.Repository

@Repository
class RestaurantRepoDB(
        val restaurantDataMapper: RestaurantDataMapper,
        val categoryDataMapper: CategoryDataMapper
): RestaurantRepo {
    override fun all(): List<RestaurantModelDB> {
        return restaurantDataMapper.all()
    }

    override fun get(id: Long): RestaurantModel {
        val restaurant = restaurantDataMapper.get(id)
        val categories = categoryDataMapper.where(restaurant.categoryIds)
        return RestaurantModel(restaurant, categories)
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        return restaurantDataMapper.create(restaurantModelNew)
    }

    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        restaurantDataMapper.update(id, restaurantModelNew)
    }

    override fun addCategory(id: Long, categoryId: Long) {
        restaurantDataMapper.addCategory(id, categoryId)
    }

    override fun destroy(id: Long) {
        restaurantDataMapper.destroy(id)
    }
}