package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapper
import io.umehara.lunchFinderServer.restaurant.RestaurantModelDB
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class CategoryRepoDB(
        val dataMapper: CategoryDataMapper,
        val restaurantDataMapper: RestaurantDataMapper
): CategoryRepo {
    override fun all(): List<CategoryModelDB> {
        return dataMapper.all()
    }

    override fun get(id: Long): CategoryModel {
        val category = dataMapper.get(id)
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


        return CategoryModel(category.id, category.name, category.restaurantCount, restaurantModels)
    }

    override fun create(categoryModelNew: CategoryModelNew): HashMap<String, Long> {
        val categoryId = dataMapper.create(categoryModelNew)
        val hashMap = HashMap<String, Long>()
        hashMap["id"] = categoryId
        return hashMap
    }

    @Transactional
    override fun removeRestaurant(id: Long, restaurantId: Long) {
        restaurantDataMapper.removeCategory(restaurantId, id)
        dataMapper.decrement(id)
    }

    override fun destroy(id: Long) {
        dataMapper.destroy(id)
    }
}