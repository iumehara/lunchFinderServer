package io.umehara.lunchFinderServer.restaurant

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.Arrays.asList


@Repository
class RestaurantDataMapperJdbc(val jdbcTemplate: JdbcTemplate): RestaurantDataMapper {
    override fun all(): List<RestaurantModelDB> {
        val sql = "SELECT * FROM restaurants"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> restaurantRowMapper(rs) }
        )
    }

    override fun get(id: Long): RestaurantModelDB {
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

        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("name", restaurantModelNew.name)
        parameterSource.addValue("category_ids", kotlinListToSqlArray(restaurantModelNew.categoryIds))

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).toLong()
    }

    private fun restaurantRowMapper(rs: ResultSet): RestaurantModelDB {
        return RestaurantModelDB(
                rs.getLong("id"),
                rs.getString("name"),
                sqlArrayToKotlinList(rs.getArray("category_ids"))
        )
    }

    private fun kotlinListToSqlArray(kotlinList: List<Long>): java.sql.Array? {
        val kotlinArray = kotlinList.toTypedArray()
        return jdbcTemplate.dataSource.connection.createArrayOf("INT", kotlinArray)
    }

    private fun sqlArrayToKotlinList(sqlArray: java.sql.Array): List<Long> {
        val kotlinArray =  sqlArray.getArray() as Array<Long>
        return asList(*kotlinArray)
    }
}
