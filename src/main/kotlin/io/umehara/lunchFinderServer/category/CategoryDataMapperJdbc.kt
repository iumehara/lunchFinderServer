package io.umehara.lunchFinderServer.category

import io.umehara.lunchFinderServer.restutils.BadRequestException
import io.umehara.lunchFinderServer.restutils.ExceptionMessage
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class CategoryDataMapperJdbc(val jdbcTemplate: JdbcTemplate): CategoryDataMapper {
    override fun all(): List<CategoryModelDB> {
        val sql = "SELECT * " +
                "FROM categories " +
                "ORDER BY restaurant_count DESC"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> categoryRowMapper(rs) }
        )
    }

    override fun where(ids: List<Long>): List<CategoryModelDB> {
        if (ids.isEmpty()) {
            return emptyList()
        }

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)
        val sql = "SELECT * " +
                "FROM categories " +
                "WHERE id in (:ids) " +
                "ORDER BY restaurant_count DESC"
        val paramSource = MapSqlParameterSource().addValue("ids", ids)

        return namedParameterJdbcTemplate.query(
                sql,
                paramSource,
                { rs, _ -> categoryRowMapper(rs) }
        )
    }

    override fun get(id: Long): CategoryModelDB? {
        val sql = "SELECT * FROM categories WHERE id=?"

        return jdbcTemplate.queryForObject(
                sql,
                { rs, _ -> categoryRowMapper(rs) },
                arrayOf(id)
        )
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")

                .usingGeneratedKeyColumns("id")

        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("name", categoryModelNew.name)
        parameterSource.addValue("restaurant_count", 0)

        try {
            return simpleJdbcInsert.executeAndReturnKey(parameterSource).toLong()
        } catch (e: DuplicateKeyException) {
            throw BadRequestException(ExceptionMessage.DUPLICATE_KEY_EXCEPTION)
        }
    }

    override fun increment(id: Long) {
        val sql = "UPDATE categories " +
                "SET restaurant_count = restaurant_count + 1 " +
                "WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }

    override fun decrement(id: Long) {
        val sql = "UPDATE categories " +
                "SET restaurant_count = restaurant_count - 1 " +
                "WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }

    override fun destroy(id: Long) {
        val sql = "DELETE FROM categories WHERE id=?"
        jdbcTemplate.update(sql, id)
    }

    private fun categoryRowMapper(rs: ResultSet): CategoryModelDB {
        return CategoryModelDB(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("restaurant_count")
        )
    }
}