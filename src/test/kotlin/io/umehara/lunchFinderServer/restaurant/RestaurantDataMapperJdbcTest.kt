package io.umehara.lunchFinderServer.restaurant

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.jdbc.core.JdbcTemplate

class RestaurantDataMapperJdbcTest: RestaurantDataMapperTest() {
    override fun setupRestaurantDataMapper(seedRestaurantDBS: List<RestaurantModelDB>): RestaurantDataMapper {
        val jdbcTemplate = initializeJdbcTemplate()
        insertSeedData(seedRestaurantDBS, jdbcTemplate)

        return RestaurantDataMapperJdbc(jdbcTemplate)
    }

    private fun initializeJdbcTemplate(): JdbcTemplate {
        val dataSource = PGSimpleDataSource()
        dataSource.url = "jdbc:postgresql://localhost/lunch_finder_test"
        val jdbcTemplate = JdbcTemplate(dataSource)
        jdbcTemplate.update("TRUNCATE restaurants RESTART IDENTITY")
        return jdbcTemplate
    }

    private fun insertSeedData(seedRestaurantDBS: List<RestaurantModelDB>, jdbcTemplate: JdbcTemplate) {
        if (seedRestaurantDBS.isEmpty()) {
            return
        }

        var sql = "INSERT INTO restaurants (name) VALUES "

        seedRestaurantDBS.forEachIndexed { index, restaurant ->
            var additionalRestaurantString = "('$restaurant.name')"
            if (index != seedRestaurantDBS.size - 1) {
                additionalRestaurantString += ", "
            }
            sql += additionalRestaurantString
        }

        jdbcTemplate.execute(sql)
    }
}
