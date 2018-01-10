package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapperFake
import io.umehara.lunchFinderServer.restaurant.RestaurantModel
import io.umehara.lunchFinderServer.restaurant.RestaurantModelDB
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.*
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
        val seedCategory = CategoryModelDB(1L, "Sushi")
        fakeCategoryDataMapper.setSeedCategories(listOf(seedCategory))

        val seedRestaurant = RestaurantModelDB(1L, "Pintokona", "ぴんとこな", listOf(1L))
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(seedRestaurant))


        val category = categoryRepoDB.get(1L)


        val expectedCategory = CategoryModel(1L, "Sushi", listOf(RestaurantModel(seedRestaurant.id, seedRestaurant.name, seedRestaurant.nameJp, listOf(seedCategory))))
        assertThat(category, equalTo(expectedCategory))
    }

    @Test
    fun createPersistsNewCategory() {
        val restaurantModelNew = CategoryModelNew("Green Asia")
        val createdCategoryId = categoryRepoDB.create(restaurantModelNew)


        val categories= categoryRepoDB.all()


        assertThat(categories[0], equalTo(CategoryModelDB(createdCategoryId, "Green Asia")))
    }

    @Test(expected = Exception::class)
    fun destroyDeletesExistingCategoryById() {
        val seedCategory = CategoryModelDB(1L, "Pintokona")
        fakeCategoryDataMapper.setSeedCategories(listOf(seedCategory))


        categoryRepoDB.destroy(1L)


        categoryRepoDB.get(1L)
    }
}