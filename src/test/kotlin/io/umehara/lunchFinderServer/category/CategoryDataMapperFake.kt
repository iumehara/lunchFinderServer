package io.umehara.lunchFinderServer.category

class CategoryDataMapperFake: CategoryDataMapper {
    private var categories = mutableListOf<CategoryModelDB>()

    fun setSeedCategories(seedCategories: List<CategoryModelDB>) {
        categories.addAll(seedCategories)
    }

    fun resetSeedCategories() {
        categories.clear()
    }

    override fun all(): List<CategoryModelDB> {
        return categories
    }

    override fun where(ids: List<Long>): List<CategoryModelDB> {
        return categories.filter { ids.contains(it.id) }
    }

    override fun get(id: Long): CategoryModelDB {
        println("categories = ${categories}")
        val filteredCategories = categories.filter { it.id == id }
        return filteredCategories[0]
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        val id = categories.size + 1L
        categories.add(CategoryModelDB(id, categoryModelNew.name))
        return id
    }

    override fun destroy(id: Long) {
        categories = categories.filter { it.id != id }.toMutableList()
    }
}