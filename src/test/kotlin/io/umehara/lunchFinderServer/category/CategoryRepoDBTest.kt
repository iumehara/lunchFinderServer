package io.umehara.lunchFinderServer.category

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.*
import org.junit.Before
import org.junit.Test

class CategoryRepoDBTest {
    private lateinit var categoryRepoDB: CategoryRepoDB
    private var fakeCategoryDataMapper: CategoryDataMapperFake = CategoryDataMapperFake()

    @Before
    fun setUp() {
        categoryRepoDB = CategoryRepoDB(fakeCategoryDataMapper)
    }

    @Test
    fun allReturnsCategories() {
        val seedCategories= listOf(
                CategoryModel(1L, "Pintokona"),
                CategoryModel(2L, "Momodori")
        )
        fakeCategoryDataMapper.setSeedCategories(seedCategories)


        val categories= categoryRepoDB.all()


        assertThat(categories, equalTo(seedCategories))
    }

    @Test
    fun getReturnsCategory() {
        val seedCategory = CategoryModel(1L, "Pintokona")
        fakeCategoryDataMapper.setSeedCategories(listOf(seedCategory))


        val restaurant = categoryRepoDB.get(1L)


        assertThat(restaurant, equalTo(seedCategory))
    }

    @Test
    fun createPersistsNewCategory() {
        val restaurantModelNew = CategoryModelNew("Green Asia")
        val createdCategoryId = categoryRepoDB.create(restaurantModelNew)


        val categories= categoryRepoDB.all()


        assertThat(categories[0], equalTo(CategoryModel(createdCategoryId, "Green Asia")))
    }

    @Test(expected = Exception::class)
    fun destroyDeletesExistingCategoryById() {
        val seedCategory = CategoryModel(1L, "Pintokona")
        fakeCategoryDataMapper.setSeedCategories(listOf(seedCategory))


        categoryRepoDB.destroy(1L)


        categoryRepoDB.get(1L)
    }
}