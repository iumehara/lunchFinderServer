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
        parameterSource.addValue("name_jp", restaurantModelNew.nameJp)
        parameterSource.addValue("website", restaurantModelNew.website)
        parameterSource.addValue("geo_lat", restaurantModelNew.geolocation?.lat)
        parameterSource.addValue("geo_long", restaurantModelNew.geolocation?.long)
        parameterSource.addValue("category_ids", kotlinListToSqlArray(restaurantModelNew.categoryIds))

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).toLong()
    }

    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        var sql = "UPDATE restaurants " +
                "SET name=:name, " +
                "name_jp=:name_jp, " +
                "category_ids=:category_ids"


        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("name", restaurantModelNew.name)
        parameterSource.addValue("name_jp", restaurantModelNew.nameJp)

        if (restaurantModelNew.website != null) {
            sql += ", website=:website"
            parameterSource.addValue("website", restaurantModelNew.website)
        }

        if (restaurantModelNew.geolocation != null) {
            sql += ", geo_lat=:geo_lat, geo_long=:geo_long"
            parameterSource.addValue("geo_lat", restaurantModelNew.geolocation.lat)
            parameterSource.addValue("geo_long", restaurantModelNew.geolocation.long)
        }

        parameterSource.addValue("category_ids", kotlinListToSqlArray(restaurantModelNew.categoryIds))
        parameterSource.addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        sql += " WHERE id=:id"

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

    override fun destroy(id: Long) {
        val sql = "DELETE FROM restaurants WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }

    private fun restaurantRowMapper(rs: ResultSet): RestaurantModelDB {
        return RestaurantModelDB(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("name_jp"),
                rs.getString("website"),
                GeolocationFactory().init(
                        rs.getBigDecimal("geo_lat"),
                        rs.getBigDecimal("geo_long")
                ),
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
