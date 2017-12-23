package io.umehara.lunchFinderServer.category

import org.springframework.stereotype.Repository

@Repository
class CategoryRepoDB(val categoryDataMapper: CategoryDataMapper): CategoryRepo {
    override fun all(): List<CategoryModel> {
        return categoryDataMapper.all()
    }

    override fun get(id: Long): CategoryModel {
        return categoryDataMapper.get(id)
    }

    override fun create(categoryModelNew: CategoryModelNew): Long {
        return categoryDataMapper.create(categoryModelNew)
    }
}