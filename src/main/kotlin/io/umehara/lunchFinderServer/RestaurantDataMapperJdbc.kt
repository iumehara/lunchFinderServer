package io.umehara.lunchFinderServer

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class RestaurantDataMapperJdbc(val jdbcTemplate: JdbcTemplate): RestaurantDataMapper {
    override fun all(): List<RestaurantModel> {
        val sql = "SELECT * FROM restaurants"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> restaurantRowMapper(rs) }
        )
    }

    override fun get(id: Long): RestaurantModel {
        val sql = "SELECT * FROM restaurants WHERE id=" + id.toString()

        return jdbcTemplate.queryForObject(
                sql,
                { rs, _ -> restaurantRowMapper(rs) }
        )
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("restaurants")
                .usingGeneratedKeyColumns("id")

        val parameterSource = mutableMapOf("name" to restaurantModelNew.name)

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).toLong()
    }

    private fun restaurantRowMapper(rs: ResultSet): RestaurantModel {
        return RestaurantModel(rs.getLong("id"), rs.getString("name"))
    }
}
