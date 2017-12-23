package io.umehara.lunchFinderServer.category

interface CategoryRepo {
    fun all(): List<CategoryModel>
    fun get(id: Long): CategoryModel
    fun create(categoryModelNew: CategoryModelNew): Long
}