package io.umehara.lunchFinderServer.category

interface CategoryDataMapper {
    fun all(): List<CategoryModel>
    fun get(id: Long): CategoryModel
    fun create(categoryModelNew: CategoryModelNew): Long
}