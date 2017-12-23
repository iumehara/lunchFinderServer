package io.umehara.lunchFinderServer

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository

@Repository
class RestaurantDataMapperJdbc(val jdbcTemplate: JdbcTemplate): RestaurantDataMapper {
    override fun all(): List<RestaurantModel> {
        val sql = "SELECT * FROM restaurants"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> RestaurantModel(rs.getLong("id"), rs.getString("name")) }
        )
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("restaurants")
                .usingGeneratedKeyColumns("id")

        val parameterSource = mutableMapOf("name" to restaurantModelNew.name)

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).toLong()
    }
}