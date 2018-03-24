package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryDataMapperFake
import io.umehara.lunchFinderServer.category.CategoryFixture.Pizza
import io.umehara.lunchFinderServer.category.CategoryFixture.Spicy
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.Arrays.asList

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
        val pizzakaya = Pizzakaya().modelDB()
        val moti = Moti().modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pizzakaya, moti))


        val restaurants = restaurantRepoDB.all()


        assertThat(restaurants[0], equalTo(moti))
        assertThat(restaurants[1], equalTo(pizzakaya))
    }

    @Test
    fun getReturnsRestaurant() {
        val pizzakaya = Pizzakaya().modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pizzakaya))

        val pizza = Pizza().modelDb()
        fakeCategoryDataMapper.setSeedCategories(listOf(pizza))

        val restaurant = restaurantRepoDB.get(pizzakaya.id)


        val expectedRestaurant = RestaurantModel(
                pizzakaya.id,
                "Pizzakaya",
                "ピザカヤ",
                "pizzakaya.com",
                Geolocation(BigDecimal.valueOf(35.662265), BigDecimal.valueOf(139.726658)),
                listOf(pizza)
        )
        assertThat(restaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun createPersistsNewRestaurant() {
        val restaurantModelNew = Pintokona().modelNew()
        val createdRestaurantIdMap = restaurantRepoDB.create(restaurantModelNew)
        val createdRestaurantId = createdRestaurantIdMap["id"]!!

        val restaurants = restaurantRepoDB.all()


        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pintokona",
                "ぴんとこな",
                null,
                null,
                listOf(4L)
        )
        assertThat(restaurants[0], equalTo(expectedRestaurant))
    }

    @Test
    fun updateUpdatesExistingRestaurant() {
        val pizza = Pizza(restaurantCount = 1).modelDb()
        val spicy = Spicy(restaurantCount = 0).modelDb()
        fakeCategoryDataMapper.setSeedCategories(asList(pizza, spicy))

        val originalRestaurant = Pizzakaya(categoryIds = asList(pizza.id)).modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(originalRestaurant))

        val editedRestaurant = RestaurantModelNew(
                "Pintokonai",
                "ぴんとこない",
                null,
                null,
                listOf(pizza.id, spicy.id)
        )


        restaurantRepoDB.update(originalRestaurant.id, editedRestaurant)


        val updatedSpicy = fakeCategoryDataMapper.get(spicy.id)
        assertThat(updatedSpicy.restaurantCount, equalTo(1L))

        val restaurant = restaurantRepoDB.get(originalRestaurant.id)
        val expectedRestaurant = RestaurantModel(
                originalRestaurant.id,
                editedRestaurant.name,
                editedRestaurant.nameJp,
                originalRestaurant.website,
                originalRestaurant.geolocation,
                asList(pizza, updatedSpicy)
        )

        assertThat(restaurant, equalTo(expectedRestaurant))
    }

    @Test
    fun addCategoryAddsCategoryToRestaurant() {
        val createdRestaurantIdMap = restaurantRepoDB.create(Pintokona().modelNew())
        val createdRestaurantId = createdRestaurantIdMap["id"]!!

        restaurantRepoDB.addCategory(createdRestaurantId, 33L)


        val restaurants = restaurantRepoDB.all()
        val expectedRestaurant = RestaurantModelDB(
                createdRestaurantId,
                "Pintokona",
                "ぴんとこな",
                null,
                null,
                listOf(4L, 33L))
        assertThat(restaurants[0], equalTo(expectedRestaurant))
    }

    @Test
    fun destroyDeletesRestaurant() {
        val pintokona = Pintokona().modelNew()
        val createdRestaurantIdMap = restaurantRepoDB.create(pintokona)
        val createdRestaurantId = createdRestaurantIdMap["id"]!!

        restaurantRepoDB.destroy(createdRestaurantId)


        val restaurants = restaurantRepoDB.all()
        assertThat(restaurants.size, equalTo(0))
    }

    @Test
    fun updateNewCategoryIdsAddsOne() {
        val pizza = Pizza(restaurantCount = 1).modelDb()
        fakeCategoryDataMapper.setSeedCategories(asList(pizza))

        val pizzakaya = Pizzakaya(categoryIds = asList(pizza.id)).modelDB()
        val saizeriya = RestaurantModelDB(2, "Saizeriya", "", null, null, emptyList())
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pizzakaya, saizeriya))



        restaurantRepoDB.updateNewCategoryIds(saizeriya.id, asList(pizza.id))




        val updatedPizza = fakeCategoryDataMapper.get(pizza.id)
        assertThat(updatedPizza.restaurantCount, equalTo(2L))
    }

    @Test
    fun updateNewCategoryIdsRemovesOne() {
        val pizza = Pizza(restaurantCount = 1).modelDb()
        fakeCategoryDataMapper.setSeedCategories(asList(pizza))

        val pizzakaya = Pizzakaya(categoryIds = asList(pizza.id)).modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pizzakaya))



        restaurantRepoDB.updateNewCategoryIds(pizzakaya.id, emptyList())




        val updatedPizza = fakeCategoryDataMapper.get(pizza.id)
        assertThat(updatedPizza.restaurantCount, equalTo(0L))
    }

    @Test
    fun updateNewCategoryIdsNoChange() {
        val pizza = Pizza(restaurantCount = 1).modelDb()
        fakeCategoryDataMapper.setSeedCategories(asList(pizza))

        val pizzakaya = Pizzakaya(categoryIds = asList(pizza.id)).modelDB()
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pizzakaya))



        restaurantRepoDB.updateNewCategoryIds(pizzakaya.id, asList(pizza.id))




        val updatedPizza = fakeCategoryDataMapper.get(pizza.id)
        assertThat(updatedPizza.restaurantCount, equalTo(1L))
    }
}
