package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapper
import org.springframework.stereotype.Repository

@Repository
class CategoryRepoDB(
        val categoryDataMapper: CategoryDataMapper,
        val restaurantDataMapper: RestaurantDataMapper
): CategoryRepo {
    override fun all(): List<CategoryModelDB> {
        return categoryDataMapper.all()
    }

    override fun get(id: Long): CategoryModel {
        val category = categoryDataMapper.get(id)
        val restaurants = restaurantDataMapper.allByCategoryId(id)
        return CategoryModel(category.id, category.name, restaurants)
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        return categoryDataMapper.create(categoryModelNew)
    }

    override fun destroy(id: Long) {
        categoryDataMapper.destroy(id)
    }
}