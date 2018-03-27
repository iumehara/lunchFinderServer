package io.umehara.lunchFinderServer.category

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.jdbc.core.JdbcTemplate

class CategoryDataMapperJdbcTest: CategoryDataMapperTest() {
    override fun setupCategoryDataMapper(seedCategories: List<CategoryModelDB>): CategoryDataMapper {
        val jdbcTemplate = initializeJdbcTemplate()
        insertSeedData(seedCategories, jdbcTemplate)

        return CategoryDataMapperJdbc(jdbcTemplate)
    }

    private fun initializeJdbcTemplate(): JdbcTemplate {
        val dataSource = PGSimpleDataSource()
        dataSource.setURL("jdbc:postgresql://localhost/lunch_finder_test")
        val jdbcTemplate = JdbcTemplate(dataSource)
        jdbcTemplate.update("TRUNCATE categories RESTART IDENTITY")
        return jdbcTemplate
    }

    private fun insertSeedData(seedCategories: List<CategoryModelDB>, jdbcTemplate: JdbcTemplate) {
        if (seedCategories.isEmpty()) {
            return
        }

        var sql = "INSERT INTO restaurants (name) VALUES "

        seedCategories.forEachIndexed { index, category ->
            var additionalCategoryString = "('$category.name')"
            if (index != seedCategories.size - 1) {
                additionalCategoryString += ", "
            }
            sql += additionalCategoryString
        }

        jdbcTemplate.execute(sql)
    }
}
