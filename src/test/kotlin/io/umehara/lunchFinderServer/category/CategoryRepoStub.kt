package io.umehara.lunchFinderServer.category

class CategoryRepoStub: CategoryRepo {
    override fun all(): List<CategoryModel> {
        return listOf(
                CategoryModel(1, "Curry"),
                CategoryModel(2, "Sushi")
        )
    }

    override fun get(id: Long): CategoryModel {
        return CategoryModel(1, "Curry")
    }

    var createArgument: CategoryModelNew? = null
    override fun create(categoryModelNew: CategoryModelNew): Long {
        createArgument = categoryModelNew
        return 1L
    }

    var destroyArgument: Long? = null
    override fun destroy(id: Long) {
        destroyArgument = id
    }
}