package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryFixture.*
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

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
    fun allByCategoryIdReturnsRestaurants() {
        val spicy = Spicy().modelDb()
        val curry = Curry().modelDb()
        val sushi = Sushi().modelDb()
        restaurantDataMapper.create(Pizzakaya(categories = listOf(spicy)).modelNew())
        restaurantDataMapper.create(Moti(categories = listOf(spicy, curry)).modelNew())
        restaurantDataMapper.create(Pintokona(categories = listOf(sushi)).modelNew())


        val restaurants = restaurantDataMapper.allByCategoryId(spicy.id)


        assertThat(restaurants.size, equalTo(2))
        assertThat(restaurants[0].name, equalTo("Pizzakaya"))
        assertThat(restaurants[1].name, equalTo("Moti"))
    }

    @Test
    fun createAndGetOneRestaurant() {
        val restaurantId = restaurantDataMapper.create(Pintokona().modelNew())


        val actualRestaurant = restaurantDataMapper.get(restaurantId)


        val expectedRestaurant = RestaurantModelDB(restaurantId, "Pintokona", "ぴんとこな", null, null, listOf(4L))
        assertThat(actualRestaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createAndGetMultipleRestaurants() {
        restaurantDataMapper.create(Pizzakaya().modelNew())
        restaurantDataMapper.create(Moti().modelNew())


        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(2))
        val geoLocation = GeoLocation(BigDecimal.valueOf(35.662265), BigDecimal.valueOf(139.726658))
        assertThat(restaurants[0], equalTo(RestaurantModelDB(1, "Pizzakaya", "ピザカヤ", "pizzakaya.com", geoLocation, listOf(1L))))
        assertThat(restaurants[1], equalTo(RestaurantModelDB(2, "Moti", "モティ",null,null, listOf(3L, 2L))))
    }

    @Test
    fun createUpdateAndGetRestaurant() {
        val newRestaurant = Pizzakaya().modelNew()
        val createdRestaurantId = restaurantDataMapper.create(newRestaurant)

        val editedRestaurant = RestaurantModelNew(
                "Pizzakaya2",
                "ピザカヤ２",
                "pizzakaya2.com",
                GeoLocation(BigDecimal.valueOf(99),BigDecimal.valueOf(99)),
                listOf(99L)
        )
        restaurantDataMapper.update(createdRestaurantId, editedRestaurant)

        val restaurant = restaurantDataMapper.get(createdRestaurantId)
        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pizzakaya2",
                "ピザカヤ２",
                "pizzakaya2.com",
                GeoLocation(BigDecimal.valueOf(99),BigDecimal.valueOf(99)),
                listOf(99L)
        )
        assertThat(restaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createUpdateOnlyRequiredFieldsAndGetRestaurant() {
        val newRestaurant = Pizzakaya().modelNew()
        val createdRestaurantId = restaurantDataMapper.create(newRestaurant)

        val editedRestaurant = RestaurantModelNew(
                "Pizzakaya2",
                "ピザカヤ２",
                null,
                null,
                listOf(99L)
        )
        restaurantDataMapper.update(createdRestaurantId, editedRestaurant)

        val restaurant = restaurantDataMapper.get(createdRestaurantId)
        assertNotNull(restaurant.website)
        assertNotNull(restaurant.geoLocation)
    }

    @Test
    fun createAddCategoryAndGetRestaurant() {
        val newRestaurant = Pizzakaya().modelNew()
        val createdRestaurantId = restaurantDataMapper.create(newRestaurant)

        restaurantDataMapper.addCategory(createdRestaurantId, 555L)

        val actualRestaurant = restaurantDataMapper.get(createdRestaurantId)
        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pizzakaya",
                "ピザカヤ",
                "pizzakaya.com",
                GeoLocation(BigDecimal.valueOf(35.662265),BigDecimal.valueOf(139.726658)),
                listOf(1L, 555L)
        )
        assertThat(actualRestaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createAndDeleteRestaurant() {
        val newRestaurant = Pizzakaya().modelNew()
        val createdRestaurantId = restaurantDataMapper.create(newRestaurant)

        restaurantDataMapper.destroy(createdRestaurantId)

        val restaurants = restaurantDataMapper.all()
        assertThat(restaurants.size, equalTo(0))
    }
}
