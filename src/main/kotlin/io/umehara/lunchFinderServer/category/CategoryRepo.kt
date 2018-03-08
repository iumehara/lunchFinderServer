package io.umehara.lunchFinderServer.category

interface CategoryRepo {
    fun all(): List<CategoryModelDB>
    fun get(id: Long): CategoryModel
    fun create(categoryModelNew: CategoryModelNew): HashMap<String, Long>
    fun removeRestaurant(id: Long, restaurantId: Long)
    fun destroy(id: Long)
}