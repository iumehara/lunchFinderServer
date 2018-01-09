package io.umehara.lunchFinderServer.restaurant

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
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

    override fun allByCategoryId(categoryId: Long): List<RestaurantModelDB> {
        val sql = "SELECT * FROM restaurants WHERE ? = ANY (category_ids)"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> restaurantRowMapper(rs) },
                arrayOf(categoryId)
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

    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        val sql = "UPDATE restaurants " +
                "SET name=:name, " +
                "category_ids=:category_ids " +
                "WHERE id=:id"

        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("name", restaurantModelNew.name)
        parameterSource.addValue("category_ids", kotlinListToSqlArray(restaurantModelNew.categoryIds))
        parameterSource.addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        namedParameterJdbcTemplate.update(
                sql,
                parameterSource
        )
    }

    override fun addCategory(id: Long, categoryId: Long) {
        val sql = "UPDATE restaurants " +
                "SET category_ids=array_append(category_ids, :category_id) " +
                "WHERE id=:id"

        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("category_id", categoryId)
        parameterSource.addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        namedParameterJdbcTemplate.update(
                sql,
                parameterSource
        )
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
        val kotlinArray =  sqlArray.array as Array<*>
        val kotlinList = asList(*kotlinArray)
        return kotlinList.filterIsInstance<Long>()
    }
}
