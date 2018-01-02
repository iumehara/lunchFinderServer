package io.umehara.lunchFinderServer.category

interface CategoryDataMapper {
    fun all(): List<CategoryModelDB>
    fun where(ids: List<Long>): List<CategoryModelDB>
    fun get(id: Long): CategoryModelDB
    fun create(categoryModelNew: CategoryModelNew): Long
    fun destroy(id: Long)
}