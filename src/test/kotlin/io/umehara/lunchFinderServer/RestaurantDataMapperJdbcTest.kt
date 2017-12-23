package io.umehara.lunchFinderServer

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.jdbc.core.JdbcTemplate

class RestaurantDataMapperJdbcTest: RestaurantDataMapperTest() {
    override fun setupRestaurantDataMapper(seedRestaurants: List<RestaurantModel>): RestaurantDataMapper {
        val jdbcTemplate = initializeJdbcTemplate()
        insertSeedData(seedRestaurants, jdbcTemplate)

        return RestaurantDataMapperJdbc(jdbcTemplate)
    }

    private fun initializeJdbcTemplate(): JdbcTemplate {
        val dataSource = PGSimpleDataSource()
        dataSource.url = "jdbc:postgresql://localhost/lunch_finder_test"
        val jdbcTemplate = JdbcTemplate(dataSource)
        jdbcTemplate.update("TRUNCATE restaurants RESTART IDENTITY")
        return jdbcTemplate
    }

    private fun insertSeedData(seedRestaurants: List<RestaurantModel>, jdbcTemplate: JdbcTemplate) {
        if (seedRestaurants.isEmpty()) {
            return
        }

        var sql = "INSERT INTO restaurants (name) VALUES "

        seedRestaurants.forEachIndexed { index, restaurant ->
            var additionalRestaurantString = "('$restaurant.name')"
            if (index != seedRestaurants.size - 1) {
                additionalRestaurantString += ", "
            }
            sql += additionalRestaurantString
        }

        jdbcTemplate.execute(sql)
    }
}
