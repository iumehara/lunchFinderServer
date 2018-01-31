package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapper
import io.umehara.lunchFinderServer.restaurant.RestaurantModel
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
        val restaurantModelDBs = restaurantDataMapper.allByCategoryId(id)

        val categoryIdsSet = restaurantModelDBs.map { it.categoryIds }.flatten().toSet()
        val categories = categoryDataMapper.where(categoryIdsSet.toList())
        val categoriesHash = categories.associateBy({ it.id }, { it })

        val restaurantModels = restaurantModelDBs.map {
            RestaurantModel(
                    it.id,
                    it.name,
                    it.nameJp,
                    it.website,
                    it.geoLocation,
                    it.categoryIds.map { categoriesHash[it] as CategoryModelDB }
            )
        }


        return CategoryModel(category.id, category.name, restaurantModels)
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        return categoryDataMapper.create(categoryModelNew)
    }

    override fun destroy(id: Long) {
        categoryDataMapper.destroy(id)
    }
}