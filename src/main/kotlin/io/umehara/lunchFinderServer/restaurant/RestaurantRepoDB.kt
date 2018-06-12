package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryDataMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RestaurantRepoDB(
        val dataMapper: RestaurantDataMapper,
        val categoryDataMapper: CategoryDataMapper
): RestaurantRepo {
    override fun all(): List<RestaurantModelDB> {
        return dataMapper.all()
    }

    override fun allFull(): List<RestaurantModel> {
        val categoriesMap = categoryDataMapper
                .all()
                .map { it.id to it }
                .toMap()

        return dataMapper.all()
                .map {restaurantModelDB ->
                    RestaurantModel(
                            restaurantModelDB,
                            restaurantModelDB
                                    .categoryIds
                                    .filter { categoryId -> categoriesMap[categoryId] != null}
                                    .map { categoryId -> categoriesMap[categoryId]!! }
                                    .toList()
                    )}
                .toList()
    }

    override fun where(categoryId: Long): List<RestaurantModelDB> {
        return dataMapper.allByCategoryId(categoryId)
    }

    override fun whereFull(categoryId: Long): List<RestaurantModel> {
        val categoriesMap = categoryDataMapper
                .all()
                .map { it.id to it }
                .toMap()

        return dataMapper.allByCategoryId(categoryId)
                .map {restaurantModelDB ->
                    RestaurantModel(
                            restaurantModelDB,
                            restaurantModelDB
                                    .categoryIds
                                    .filter { categoryId -> categoriesMap[categoryId] != null}
                                    .map { categoryId -> categoriesMap[categoryId]!! }
                                    .toList()
                    )}
                .toList()
    }

    override fun get(id: Long): RestaurantModel {
        val restaurant = dataMapper.get(id) ?: throw Exception("No restaurant for id: $id")
        val categories = categoryDataMapper.where(restaurant.categoryIds)
        return RestaurantModel(restaurant, categories)
    }

    @Transactional
    override fun create(restaurantModelNew: RestaurantModelNew): HashMap<String, Long> {
        val restaurantId = dataMapper.create(restaurantModelNew)
        updateNewCategoryIds(restaurantId, restaurantModelNew.categoryIds)
        val hashMap = HashMap<String, Long>()
        hashMap["id"] = restaurantId
        return hashMap
    }

    @Transactional
    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        updateNewCategoryIds(id, restaurantModelNew.categoryIds)
        dataMapper.update(id, restaurantModelNew)
    }

    @Transactional
    override fun addCategory(id: Long, categoryId: Long) {
        dataMapper.addCategory(id, categoryId)
        categoryDataMapper.increment(categoryId)
    }

    @Transactional
    override fun removeCategory(id: Long, categoryId: Long) {
        dataMapper.removeCategory(id, categoryId)
        categoryDataMapper.decrement(categoryId)
    }

    override fun destroy(id: Long) {
        updateNewCategoryIds(id, emptyList())
        dataMapper.destroy(id)
    }

    fun updateNewCategoryIds(id: Long, updatedCategoryIds: List<Long>) {
        val restaurantToUpdate = dataMapper.get(id) ?: throw Exception("No restaurant for id: $id")
        val currentCategoryIds = restaurantToUpdate.categoryIds
        val addedCategoryIds = updatedCategoryIds.filter { !currentCategoryIds.contains(it) }.toList()
        val removedCategoryIds = currentCategoryIds.filter { !updatedCategoryIds.contains(it) }.toList()

        incrementCategories(addedCategoryIds)
        decrementCategories(removedCategoryIds)
    }

    private fun incrementCategories(categoryIds: List<Long>) {
        for (categoryId in categoryIds) {
            categoryDataMapper.increment(categoryId)
        }
    }

    private fun decrementCategories(categoryIds: List<Long>) {
        for (categoryId in categoryIds) {
            categoryDataMapper.decrement(categoryId)
        }
    }
}