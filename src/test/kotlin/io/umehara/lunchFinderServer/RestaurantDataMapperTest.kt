package io.umehara.lunchFinderServer

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test

abstract class RestaurantDataMapperTest {
    private lateinit var restaurantDataMapper: RestaurantDataMapper
    abstract fun setupRestaurantDataMapper(seedRestaurants: List<RestaurantModel>): RestaurantDataMapper

    @Before
    fun setUp() {
        restaurantDataMapper = setupRestaurantDataMapper(emptyList())
    }

    @Test
    fun getReturnsEmptyList() {
        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(0))
    }

    @Test
    fun createAddsOneRestaurant() {
        val restaurantModelNew = RestaurantModelNew("Momodori")
        restaurantDataMapper.create(restaurantModelNew)

        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(1))
        assertThat(restaurants[0], equalTo(RestaurantModel(1, "Momodori")))
    }

    @Test
    fun createAddsMultipleRestaurants() {
        val restaurant1 = RestaurantModelNew("Momodori")
        restaurantDataMapper.create(restaurant1)
        val restaurant2 = RestaurantModelNew("Pintokona")
        restaurantDataMapper.create(restaurant2)

        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(2))
        assertThat(restaurants[0], equalTo(RestaurantModel(1, "Momodori")))
        assertThat(restaurants[1], equalTo(RestaurantModel(2, "Pintokona")))
    }
}
