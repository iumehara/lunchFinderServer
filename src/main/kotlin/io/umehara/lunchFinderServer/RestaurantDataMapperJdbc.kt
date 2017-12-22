package io.umehara.lunchFinderServer

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class RestaurantDataMapperJdbc(val jdbcTemplate: JdbcTemplate): RestaurantDataMapper {
    override fun all(): List<Restaurant> {
        val sql = "SELECT * FROM restaurants"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> Restaurant(rs.getLong("id"), rs.getString("name")) }
        )
    }
}