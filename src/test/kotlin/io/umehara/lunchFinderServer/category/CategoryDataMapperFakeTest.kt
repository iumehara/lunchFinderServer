package io.umehara.lunchFinderServer.category

class CategoryDataMapperFakeTest: CategoryDataMapperTest() {
    override fun setupCategoryDataMapper(seedCategories: List<CategoryModelDB>): CategoryDataMapper {
        val categoryDataMapperFake = CategoryDataMapperFake()
        categoryDataMapperFake.setSeedCategories(seedCategories)
        return categoryDataMapperFake
    }
}
