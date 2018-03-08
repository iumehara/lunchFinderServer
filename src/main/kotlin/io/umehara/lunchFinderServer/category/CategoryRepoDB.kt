package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapper
import io.umehara.lunchFinderServer.restaurant.RestaurantModelDB
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
        val restaurantModels = restaurantModelDBs.map {
            RestaurantModelDB(
                    it.id,
                    it.name,
                    it.nameJp,
                    it.website,
                    it.geolocation,
                    it.categoryIds
            )
        }


        return CategoryModel(category.id, category.name, restaurantModels)
    }

    override fun create(categoryModelNew: CategoryModelNew): HashMap<String, Long> {
        val categoryId = categoryDataMapper.create(categoryModelNew)
        val hashMap = HashMap<String, Long>()
        hashMap.set("id", categoryId)
        return hashMap
    }

    override fun removeRestaurant(id: Long, restaurantId: Long) {
        restaurantDataMapper.removeCategory(restaurantId, id)
    }

    override fun destroy(id: Long) {
        categoryDataMapper.destroy(id)
    }
}