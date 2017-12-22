package io.umehara.lunchFinderServer

import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo

class RestaurantRepoDBTest {
    @Test
    fun all_returnsRestaurants() {
        val restaurantRepoDB = RestaurantRepoDB(RestaurantDataMapperFake())


        val restaurants = restaurantRepoDB.all()


        val expectedRestaurants = listOf(Restaurant(1, "Pintokona"), Restaurant(2, "Momodori"))
        assertThat(restaurants, equalTo(expectedRestaurants))
    }
}
