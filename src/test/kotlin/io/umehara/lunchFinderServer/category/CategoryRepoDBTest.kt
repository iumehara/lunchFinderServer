package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.category.CategoryFixture.Sushi
import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapperFake
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Pintokona
import io.umehara.lunchFinderServer.restaurant.RestaurantModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test

class CategoryRepoDBTest {
    private lateinit var categoryRepoDB: CategoryRepoDB
    private var fakeCategoryDataMapper: CategoryDataMapperFake = CategoryDataMapperFake()
    private var fakeRestaurantDataMapper: RestaurantDataMapperFake = RestaurantDataMapperFake()

    @Before
    fun setUp() {
        categoryRepoDB = CategoryRepoDB(fakeCategoryDataMapper, fakeRestaurantDataMapper)
    }

    @Test
    fun allReturnsCategories() {
        val seedCategories= listOf(
                CategoryModelDB(1L, "Pintokona"),
                CategoryModelDB(2L, "Momodori")
        )
        fakeCategoryDataMapper.setSeedCategories(seedCategories)


        val categories= categoryRepoDB.all()


        assertThat(categories, equalTo(seedCategories))
    }

    @Test
    fun getReturnsCategory() {
        val sushiModelDB = Sushi().modelDb()
        fakeCategoryDataMapper.setSeedCategories(listOf(sushiModelDB))

        val pintokonaModelDB = Pintokona().modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pintokonaModelDB))


        val category = categoryRepoDB.get(sushiModelDB.id)


        val expectedCategory = CategoryModel(
                4L,
                "Sushi",
                listOf(RestaurantModel(pintokonaModelDB, listOf(sushiModelDB)))
        )
        assertThat(category, equalTo(expectedCategory))
    }

    @Test
    fun createPersistsNewCategory() {
        val restaurantModelNew = CategoryModelNew("Green Asia")
        val createdCategoryIdHash = categoryRepoDB.create(restaurantModelNew)
        val categoryId = createdCategoryIdHash["id"]!!


        val categories= categoryRepoDB.all()


        assertThat(categories[0], equalTo(CategoryModelDB(categoryId, "Green Asia")))
    }

    @Test(expected = Exception::class)
    fun destroyDeletesExistingCategoryById() {
        val seedCategory = CategoryModelDB(1L, "Pintokona")
        fakeCategoryDataMapper.setSeedCategories(listOf(seedCategory))


        categoryRepoDB.destroy(1L)


        categoryRepoDB.get(1L)
    }
}