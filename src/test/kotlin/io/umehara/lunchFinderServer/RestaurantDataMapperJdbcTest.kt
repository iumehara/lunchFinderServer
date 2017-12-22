package io.umehara.lunchFinderServer

import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Test
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.jdbc.core.JdbcTemplate

class RestaurantDataMapperJdbcTest {
    @Test
    fun all_returnsRestaurants() {
        val dataSource = PGSimpleDataSource()
        dataSource.url = "jdbc:postgresql://localhost/lunch_finder_test"
        val jdbcTemplate = JdbcTemplate(dataSource)
        val restaurantDataMapperJdbc = RestaurantDataMapperJdbc(jdbcTemplate)


        val restaurants = restaurantDataMapperJdbc.all()


        val expectedRestaurants = listOf(Restaurant(1, "Pintokona"), Restaurant(2, "Momodori"))
        MatcherAssert.assertThat(restaurants, IsEqual.equalTo(expectedRestaurants))
    }
}
