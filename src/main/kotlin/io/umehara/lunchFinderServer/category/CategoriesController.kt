package io.umehara.lunchFinderServer.category

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("categories")
class CategoriesController(val repo: CategoryRepo) {
    @GetMapping
    fun index(): List<CategoryModel> {
        return repo.all()
    }

    @GetMapping("{id}")
    fun show(@PathVariable id: Long): CategoryModel {
        return repo.get(id)
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody categoryModelNew: CategoryModelNew): Long {
        return repo.create(categoryModelNew)
    }
}