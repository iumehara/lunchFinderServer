package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restutils.BadRequestException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test


abstract class CategoryDataMapperTest {
    private lateinit var categoryDataMapper: CategoryDataMapper
    abstract fun setupCategoryDataMapper(seedCategories: List<CategoryModelDB>): CategoryDataMapper

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
    fun createAndGetAllCategories() {
        val category1 = CategoryModelNew("curry")
        categoryDataMapper.create(category1)
        val category2 = CategoryModelNew("sushi")
        categoryDataMapper.create(category2)


        val categories = categoryDataMapper.all()


        assertThat(categories.size, equalTo(2))
        assertThat(categories[0], equalTo(CategoryModelDB(1, "curry")))
        assertThat(categories[1], equalTo(CategoryModelDB(2, "sushi")))
    }

    @Test
    fun createAndGetSomeCategoriesWhere() {
        val category1 = CategoryModelNew("curry")
        categoryDataMapper.create(category1)
        val category2 = CategoryModelNew("sushi")
        categoryDataMapper.create(category2)
        val category3 = CategoryModelNew("spicy")
        categoryDataMapper.create(category3)


        val categories = categoryDataMapper.where(listOf(1L, 3L))


        assertThat(categories.size, equalTo(2))
        assertThat(categories[0], equalTo(CategoryModelDB(1, "curry")))
        assertThat(categories[1], equalTo(CategoryModelDB(3, "spicy")))
    }

    @Test
    fun whereWithNoCategoriesReturnsEmptyList() {
        val categories = categoryDataMapper.where(emptyList())


        assertThat(categories.size, equalTo(0))
    }

    @Test
    fun createAndGetOneCategory() {
        val categoryModelNew = CategoryModelNew("curry")
        categoryDataMapper.create(categoryModelNew)


        val category = categoryDataMapper.get(1L)


        assertThat(category, equalTo(CategoryModelDB(1, "curry")))
    }

    @Test
    fun createIncrementAndDecrementCategory() {
        val categoryModelNew = CategoryModelNew("curry")
        val categoryId = categoryDataMapper.create(categoryModelNew)


        categoryDataMapper.increment(categoryId)
        categoryDataMapper.increment(categoryId)
        categoryDataMapper.increment(categoryId)
        categoryDataMapper.decrement(categoryId)
        val category = categoryDataMapper.get(categoryId)


        assertThat(category!!.restaurantCount, equalTo(2L))
    }

    @Test
    fun createSavesCategoryWithLowercaseName() {
        val categoryModelNew = CategoryModelNew("CuRrY")
        val categoryId = categoryDataMapper.create(categoryModelNew)


        val category = categoryDataMapper.get(categoryId)


        assertThat(category!!.name, equalTo("curry"))
    }

    @Test(expected = BadRequestException::class)
    fun createDuplicateNameThrowsException() {

        val category1 = CategoryModelNew("curry")
        categoryDataMapper.create(category1)
        categoryDataMapper.create(category1)


        val categories = categoryDataMapper.all()


        assertThat(categories.size, equalTo(1))
        assertThat(categories[0], equalTo(CategoryModelDB(1, "curry")))
    }

    @Test
    fun destroyRemovesCategory() {
        val category1 = CategoryModelNew("curry")
        categoryDataMapper.create(category1)
        val category2 = CategoryModelNew("sushi")
        categoryDataMapper.create(category2)


        categoryDataMapper.destroy(2L)


        val categories = categoryDataMapper.all()
        assertThat(categories.size, equalTo(1))
        assertThat(categories[0], equalTo(CategoryModelDB(1, "curry")))
    }
}
