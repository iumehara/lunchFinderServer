package io.umehara.lunchFinderServer.category

interface CategoryDataMapper {
    fun all(): List<CategoryModel>
    fun where(ids: List<Long>): List<CategoryModel>
    fun get(id: Long): CategoryModel
    fun create(categoryModelNew: CategoryModelNew): Long
    fun destroy(id: Long)
}