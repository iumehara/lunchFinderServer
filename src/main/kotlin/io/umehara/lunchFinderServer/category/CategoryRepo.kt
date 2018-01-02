package io.umehara.lunchFinderServer.category

interface CategoryRepo {
    fun all(): List<CategoryModelDB>
    fun get(id: Long): CategoryModel
    fun create(categoryModelNew: CategoryModelNew): Long
    fun destroy(id: Long)
}