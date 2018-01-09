package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryDataMapperFake
import io.umehara.lunchFinderServer.category.CategoryModelDB
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test

class RestaurantRepoDBTest {
    private lateinit var restaurantRepoDB: RestaurantRepoDB
    private var fakeRestaurantDataMapper: RestaurantDataMapperFake = RestaurantDataMapperFake()
    private var fakeCategoryDataMapper: CategoryDataMapperFake = CategoryDataMapperFake()

    @Before
    fun setUp() {
        restaurantRepoDB = RestaurantRepoDB(fakeRestaurantDataMapper, fakeCategoryDataMapper)
    }

    @Test
    fun allReturnsRestaurants() {
        val seedRestaurants = listOf(
                RestaurantModelDB(1L, "Pintokona", listOf(1L)),
                RestaurantModelDB(2L, "Momodori", listOf(2L))
        )
        fakeRestaurantDataMapper.setSeedRestaurants(seedRestaurants)


        val restaurants = restaurantRepoDB.all()


        assertThat(restaurants, equalTo(seedRestaurants))
    }

    @Test
    fun getReturnsRestaurant() {
        val seedRestaurant = RestaurantModelDB(1L, "Pintokona", listOf(5L))
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(seedRestaurant))

        val seedCategories = listOf(CategoryModelDB(5L, "Sushi"))
        fakeCategoryDataMapper.setSeedCategories(seedCategories)


        val restaurant = restaurantRepoDB.get(1L)
        val expectedRestaurant = RestaurantModel(1L, "Pintokona", listOf(CategoryModelDB(5L, "Sushi")))



        assertThat(restaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createPersistsNewRestaurant() {
        val restaurantModelNew = RestaurantModelNew("Green Asia", listOf(1L))
        val createdRestaurantId = restaurantRepoDB.create(restaurantModelNew)


        val restaurants = restaurantRepoDB.all()


        assertThat(restaurants[0], equalTo(RestaurantModelDB(createdRestaurantId, "Green Asia", listOf(1L))))
    }

    @Test
    fun updateUpdatesExistingRestaurant() {
        val restaurantModelNew = RestaurantModelNew("Green Asia", listOf(1L))
        val createdRestaurantId = restaurantRepoDB.create(restaurantModelNew)
        val editedRestaurantModelNew = RestaurantModelNew("Blue Asia", listOf(2L))


        restaurantRepoDB.update(createdRestaurantId, editedRestaurantModelNew)


        val restaurants = restaurantRepoDB.all()
        assertThat(restaurants[0], equalTo(RestaurantModelDB(createdRestaurantId, "Blue Asia", listOf(2L))))
    }

    @Test
    fun addCategoryAddsCategoryToRestaurant() {
        val restaurantModelNew = RestaurantModelNew("Green Asia", listOf(1L))
        val createdRestaurantId = restaurantRepoDB.create(restaurantModelNew)

        restaurantRepoDB.addCategory(createdRestaurantId, 33L)


        val restaurants = restaurantRepoDB.all()
        assertThat(restaurants[0], equalTo(
                RestaurantModelDB(createdRestaurantId, "Green Asia", listOf(1L, 33L)))
        )
    }
}
