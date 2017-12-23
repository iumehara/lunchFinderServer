package io.umehara.lunchFinderServer.restaurant

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test

class RestaurantRepoDBTest {
    private lateinit var restaurantRepoDB: RestaurantRepoDB
    private var fakeRestaurantDataMapper: RestaurantDataMapperFake = RestaurantDataMapperFake()

    @Before
    fun setUp() {
        restaurantRepoDB = RestaurantRepoDB(fakeRestaurantDataMapper)
    }

    @Test
    fun allReturnsRestaurants() {
        val seedRestaurants = listOf(
                RestaurantModel(1L, "Pintokona"),
                RestaurantModel(2L, "Momodori")
        )
        fakeRestaurantDataMapper.setSeedRestaurants(seedRestaurants)


        val restaurants = restaurantRepoDB.all()


        assertThat(restaurants, equalTo(seedRestaurants))
    }

    @Test
    fun getReturnsRestaurant() {
        val seedRestaurant = RestaurantModel(1L, "Pintokona")
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(seedRestaurant))


        val restaurant = restaurantRepoDB.get(1L)


        assertThat(restaurant, equalTo(seedRestaurant))
    }

    @Test
    fun createPersistsNewRestaurant() {
        val restaurantModelNew = RestaurantModelNew("Green Asia")
        val createdRestaurantId = restaurantRepoDB.create(restaurantModelNew)


        val restaurants = restaurantRepoDB.all()


        assertThat(restaurants[0], equalTo(RestaurantModel(createdRestaurantId, "Green Asia")))
    }
}
