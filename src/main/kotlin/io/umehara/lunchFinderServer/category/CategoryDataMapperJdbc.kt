package io.umehara.lunchFinderServer.category

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class CategoryDataMapperJdbc(val jdbcTemplate: JdbcTemplate): CategoryDataMapper {
    override fun all(): List<CategoryModel> {
        val sql = "SELECT * FROM categories"
        return jdbcTemplate.query(
                sql,
                { rs, _ -> categoryRowMapper(rs) }
        )
    }

    override fun get(id: Long): CategoryModel {
        val sql = "SELECT * FROM categories WHERE id=" + id.toString()

        return jdbcTemplate.queryForObject(
                sql,
                { rs, _ -> categoryRowMapper(rs) }
        )
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("id")

        val parameterSource = mutableMapOf("name" to categoryModelNew.name)

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).toLong()
    }

    private fun categoryRowMapper(rs: ResultSet): CategoryModel {
        return CategoryModel(rs.getLong("id"), rs.getString("name"))
    }
}