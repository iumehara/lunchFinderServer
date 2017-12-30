package io.umehara.lunchFinderServer.restaurant

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test

abstract class RestaurantDataMapperTest {
    private lateinit var restaurantDataMapper: RestaurantDataMapper
    abstract fun setupRestaurantDataMapper(seedRestaurantDBS: List<RestaurantModelDB>): RestaurantDataMapper

    @Before
    fun setUp() {
        restaurantDataMapper = setupRestaurantDataMapper(emptyList())
    }

    @Test
    fun allReturnsEmptyList() {
        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(0))
    }

    @Test
    fun createAndGetOneRestaurant() {
        val restaurantModelNew = RestaurantModelNew("Momodori", listOf(1L, 5L))
        restaurantDataMapper.create(restaurantModelNew)

        val restaurant = restaurantDataMapper.get(1L)
        assertThat(restaurant, equalTo(RestaurantModelDB(1, "Momodori", listOf(1L, 5L))))
    }

    @Test
    fun createAndGetMultipleRestaurants() {
        val restaurant1 = RestaurantModelNew("Momodori", listOf(1L))
        restaurantDataMapper.create(restaurant1)
        val restaurant2 = RestaurantModelNew("Pintokona", listOf(2L))
        restaurantDataMapper.create(restaurant2)

        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(2))
        assertThat(restaurants[0], equalTo(RestaurantModelDB(1, "Momodori", listOf(1L))))
        assertThat(restaurants[1], equalTo(RestaurantModelDB(2, "Pintokona", listOf(2L))))
    }

    @Test
    fun createUpdateAndGetRestaurant() {
        val newRestaurant = RestaurantModelNew("Momodori", listOf(1L))
        val createdRestaurantId = restaurantDataMapper.create(newRestaurant)

        val editedRestaurant = RestaurantModelNew("百鳥", listOf(2L))
        restaurantDataMapper.update(createdRestaurantId, editedRestaurant)

        val restaurant = restaurantDataMapper.get(createdRestaurantId)
        assertThat(restaurant, equalTo(RestaurantModelDB(
                createdRestaurantId,
                "百鳥",
                listOf(2L)
        )))
    }
}
