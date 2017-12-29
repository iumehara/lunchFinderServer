package io.umehara.lunchFinderServer.category

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test

abstract class CategoryDataMapperTest {
    private lateinit var categoryDataMapper: CategoryDataMapper
    abstract fun setupCategoryDataMapper(seedCategories: List<CategoryModel>): CategoryDataMapper

    @Before
    fun setUp() {
        categoryDataMapper = setupCategoryDataMapper(emptyList())
    }

    @Test
    fun allReturnsEmptyList() {
        val categories = categoryDataMapper.all()


        assertThat(categories.size, equalTo(0))
    }

    @Test
    fun createAndGetOneCategory() {
        val categoryModelNew = CategoryModelNew("Curry")
        categoryDataMapper.create(categoryModelNew)


        val category = categoryDataMapper.get(1L)


        assertThat(category, equalTo(CategoryModel(1, "Curry")))
    }

    @Test
    fun createAndGetAllCategories() {
        val category1 = CategoryModelNew("Curry")
        categoryDataMapper.create(category1)
        val category2 = CategoryModelNew("Sushi")
        categoryDataMapper.create(category2)


        val categories = categoryDataMapper.all()


        assertThat(categories.size, equalTo(2))
        assertThat(categories[0], equalTo(CategoryModel(1, "Curry")))
        assertThat(categories[1], equalTo(CategoryModel(2, "Sushi")))
    }

    @Test
    fun createAndGetSomeCategoriesWhere() {
        val category1 = CategoryModelNew("Curry")
        categoryDataMapper.create(category1)
        val category2 = CategoryModelNew("Sushi")
        categoryDataMapper.create(category2)
        val category3 = CategoryModelNew("Spicy")
        categoryDataMapper.create(category3)


        val categories = categoryDataMapper.where(listOf(1L, 3L))


        assertThat(categories.size, equalTo(2))
        assertThat(categories[0], equalTo(CategoryModel(1, "Curry")))
        assertThat(categories[1], equalTo(CategoryModel(3, "Spicy")))
    }

    @Test
    fun destroyRemovesCategory() {
        val category1 = CategoryModelNew("Curry")
        categoryDataMapper.create(category1)
        val category2 = CategoryModelNew("Sushi")
        categoryDataMapper.create(category2)


        categoryDataMapper.destroy(2L)


        val categories = categoryDataMapper.all()
        assertThat(categories.size, equalTo(1))
        assertThat(categories[0], equalTo(CategoryModel(1, "Curry")))
    }
}
