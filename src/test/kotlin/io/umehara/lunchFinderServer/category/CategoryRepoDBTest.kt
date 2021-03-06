package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.category.CategoryFixture.Sushi
import io.umehara.lunchFinderServer.restaurant.RestaurantDataMapperFake
import io.umehara.lunchFinderServer.restaurant.RestaurantFixture.Pintokona
import io.umehara.lunchFinderServer.restaurant.RestaurantModelDB
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import java.util.Arrays.asList

class CategoryRepoDBTest {
    private lateinit var categoryRepoDB: CategoryRepoDB
    private var fakeCategoryDataMapper: CategoryDataMapperFake = CategoryDataMapperFake()
    private var fakeRestaurantDataMapper: RestaurantDataMapperFake = RestaurantDataMapperFake()

    @Before
    fun setUp() {
        fakeRestaurantDataMapper.resetSeedRestaurants()
        fakeCategoryDataMapper.resetSeedCategories()
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
                0L,
                listOf(pintokonaModelDB)
        )
        assertThat(category, equalTo(expectedCategory))
    }

    @Test
    fun createPersistsNewCategory() {
        val categoryModelNew = CategoryModelNew("sushi")
        val createdCategoryIdHash = categoryRepoDB.create(categoryModelNew)
        val categoryId = createdCategoryIdHash["id"]!!


        val categories= categoryRepoDB.all()


        assertThat(categories[0], equalTo(CategoryModelDB(categoryId, "sushi")))
    }

    @Test
    fun removeRestaurantRemovesRelatedRestaurant() {
        val sushiModelDB = Sushi(1, 2).modelDb()
        fakeCategoryDataMapper.setSeedCategories(listOf(sushiModelDB))

        val pintokonaModelDB = Pintokona(1, asList(sushiModelDB.id)).modelDB()
        val kuraModelDB = RestaurantModelDB(2, "kura", "", "", null, asList(sushiModelDB.id))
        fakeRestaurantDataMapper.setSeedRestaurants(listOf(pintokonaModelDB, kuraModelDB))

        categoryRepoDB.removeRestaurant(sushiModelDB.id, kuraModelDB.id)
        val sushiCategory = categoryRepoDB.get(sushiModelDB.id)


        assertThat(sushiCategory.restaurants.size, equalTo(1))
        assertThat(sushiCategory.restaurantCount, equalTo(1L))
        assertThat(sushiCategory.restaurants[0], equalTo(pintokonaModelDB))
    }

    @Test(expected = Exception::class)
    fun destroyDeletesExistingCategoryById() {
        val seedCategory = CategoryModelDB(1L, "Pintokona")
        fakeCategoryDataMapper.setSeedCategories(listOf(seedCategory))


        categoryRepoDB.destroy(1L)


        categoryRepoDB.get(1L)
    }
}