package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryFixture.*
import io.umehara.lunchFinderServer.category.CategoryModelDB

class RestaurantFixture {
    class Pizzakaya(
            val id: Long = 1,
            val categories: List<CategoryModelDB> = listOf(Pizza().modelDb(), Spicy().modelDb())
    ) {
        val name = "Pizzakaya"
        private val nameJp = "ピザカヤ"
        fun model() = RestaurantModel(id, name, nameJp, categories)
        fun modelDB() = RestaurantModelDB(id, name, nameJp, categories.map { it.id })
        fun modelNew() = RestaurantModelNew(name, nameJp, categories.map { it.id })
    }

    class Moti(
            val id: Long = 2,
            val categories: List<CategoryModelDB> = listOf(Curry().modelDb(), Spicy().modelDb())
    ) {
        val name = "Moti"
        private val nameJp = "モティ"
        fun modelDB() = RestaurantModelDB(id, name, nameJp, categories.map { it.id })
        fun modelNew() = RestaurantModelNew(name, nameJp, categories.map { it.id })
    }

    class Pintokona(
            val id: Long = 3,
            val categories: List<CategoryModelDB> = listOf(Sushi().modelDb())
    ) {
        val name = "Pintokona"
        private val nameJp = "ぴんとこな"
        fun modelDB() = RestaurantModelDB(id, name, nameJp, categories.map { it.id })
        fun modelNew() = RestaurantModelNew(name, nameJp, categories.map { it.id })
    }
}