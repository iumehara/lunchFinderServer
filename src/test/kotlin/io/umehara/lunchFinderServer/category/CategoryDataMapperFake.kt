package io.umehara.lunchFinderServer.category

class CategoryDataMapperFake: CategoryDataMapper {
    private var categories = mutableListOf<CategoryModel>()

    fun setSeedCategories(seedCategories: List<CategoryModel>) {
        categories.addAll(seedCategories)
    }

    override fun all(): List<CategoryModel> {
        return categories
    }

    override fun where(ids: List<Long>): List<CategoryModel> {
        return categories.filter { ids.contains(it.id) }
    }

    override fun get(id: Long): CategoryModel {
        val filteredCategories = categories.filter { it.id == id }
        return filteredCategories[0]
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        val id = categories.size + 1L
        categories.add(CategoryModel(id, categoryModelNew.name))
        return id
    }
}