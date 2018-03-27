package io.umehara.lunchFinderServer.restaurant

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
    fun allReturnsRestaurants() {
        restaurantDataMapper.create(Pizzakaya(categoryIds = listOf(1)).modelNew())
        restaurantDataMapper.create(Moti(categoryIds = listOf(1, 2)).modelNew())
        restaurantDataMapper.create(Pintokona(categoryIds = listOf(2, 3)).modelNew())


        val restaurants = restaurantDataMapper.all()


        assertThat(restaurants.size, equalTo(3))
        assertThat(restaurants[0].name, equalTo("Moti"))
        assertThat(restaurants[1].name, equalTo("Pintokona"))
        assertThat(restaurants[2].name, equalTo("Pizzakaya"))
    }


    @Test
    fun allByCategoryIdReturnsRestaurants() {
        restaurantDataMapper.create(Pizzakaya(categoryIds = listOf(1)).modelNew())
        restaurantDataMapper.create(Moti(categoryIds = listOf(1, 2)).modelNew())
        restaurantDataMapper.create(Pintokona(categoryIds = listOf(2, 3)).modelNew())


        val restaurants = restaurantDataMapper.allByCategoryId(1)


        assertThat(restaurants.size, equalTo(2))
        assertThat(restaurants[0].name, equalTo("Moti"))
        assertThat(restaurants[1].name, equalTo("Pizzakaya"))
    }

    @Test
    fun getOneRestaurant() {
        val restaurantModelNew = Pintokona().modelNew()
        val pintokonaId = restaurantDataMapper.create(restaurantModelNew)
        restaurantDataMapper.create(Pizzakaya().modelNew())


        val actualRestaurant = restaurantDataMapper.get(pintokonaId)


        val expectedRestaurant = Pintokona(id = pintokonaId).modelDB()
        assertThat(actualRestaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun updateAndGetRestaurant() {
        val newRestaurant = RestaurantModelNew(
                "Pizzakaya",
                "ピザカヤ",
                "pizzakaya.com",
                Geolocation(BigDecimal.valueOf(1),BigDecimal.valueOf(1)),
                listOf(1L)
        )

        val pizzakayaId = restaurantDataMapper.create(newRestaurant)

        val editedRestaurant = RestaurantModelNew(
                "Pizzakaya2",
                "ピザカヤ２",
                "pizzakaya2.com",
                Geolocation(BigDecimal.valueOf(99),BigDecimal.valueOf(99)),
                listOf(99L)
        )
        restaurantDataMapper.update(pizzakayaId, editedRestaurant)

        val restaurant = restaurantDataMapper.get(pizzakayaId)
        val expectedRestaurant = RestaurantModelDB(
                pizzakayaId,
                editedRestaurant.name,
                editedRestaurant.nameJp,
                editedRestaurant.website,
                editedRestaurant.geolocation,
                editedRestaurant.categoryIds
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
        assertNotNull(restaurant!!.website)
        assertNotNull(restaurant.geolocation)
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
                Geolocation(BigDecimal.valueOf(35.662265),BigDecimal.valueOf(139.726658)),
                listOf(1L, 555L)
        )
        assertThat(actualRestaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createRemoveCategoryAndGetRestaurant() {
        val newRestaurant = Pizzakaya().modelNew()
        val createdRestaurantId = restaurantDataMapper.create(newRestaurant)

        restaurantDataMapper.removeCategory(createdRestaurantId, 1L)

        val actualRestaurant = restaurantDataMapper.get(createdRestaurantId)
        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pizzakaya",
                "ピザカヤ",
                "pizzakaya.com",
                Geolocation(BigDecimal.valueOf(35.662265),BigDecimal.valueOf(139.726658)),
                emptyList()
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
