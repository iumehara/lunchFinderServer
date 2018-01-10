package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryDataMapperFake
import io.umehara.lunchFinderServer.category.CategoryFixture
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.*
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
                Pizzakaya().modelDB(),
                Moti().modelDB()
        )
        fakeRestaurantDataMapper.setSeedRestaurants(seedRestaurants)


        val restaurants = restaurantRepoDB.all()


        assertThat(restaurants, equalTo(seedRestaurants))
    }

    @Test
    fun getReturnsRestaurant() {
        val pintokona = Pintokona().modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pintokona))

        val sushi = CategoryFixture.Sushi().modelDb()
        fakeCategoryDataMapper.setSeedCategories(listOf(sushi))


        val restaurant = restaurantRepoDB.get(pintokona.id)


        val expectedRestaurant = RestaurantModel(
                pintokona.id,
                "Pintokona",
                "ぴんとこな",
                listOf(sushi)
        )
        assertThat(restaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createPersistsNewRestaurant() {
        val restaurantModelNew = Pintokona().modelNew()
        val createdRestaurantId = restaurantRepoDB.create(restaurantModelNew)


        val restaurants = restaurantRepoDB.all()


        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pintokona",
                "ぴんとこな",
                listOf(4L)
        )
        assertThat(restaurants[0], equalTo(expectedRestaurant))
    }

    @Test
    fun updateUpdatesExistingRestaurant() {
        val createdRestaurantId = restaurantRepoDB.create(Pintokona().modelNew())
        val editedRestaurantModelNew = RestaurantModelNew("Pintokonai", "ぴんとこない", listOf(99L))


        restaurantRepoDB.update(createdRestaurantId, editedRestaurantModelNew)


        val restaurants = restaurantRepoDB.all()
        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pintokonai",
                "ぴんとこない",
                listOf(99L)
        )
        assertThat(restaurants[0], equalTo(expectedRestaurant))
    }

    @Test
    fun addCategoryAddsCategoryToRestaurant() {
        val createdRestaurantId = restaurantRepoDB.create(Pintokona().modelNew())

        restaurantRepoDB.addCategory(createdRestaurantId, 33L)


        val restaurants = restaurantRepoDB.all()
        val expectedRestaurant = RestaurantModelDB(createdRestaurantId, "Pintokona", "ぴんとこな", listOf(4L, 33L))
        assertThat(restaurants[0], equalTo(expectedRestaurant))
    }
}
