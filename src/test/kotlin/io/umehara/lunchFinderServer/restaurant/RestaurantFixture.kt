package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryFixture.*
import io.umehara.lunchFinderServer.category.CategoryModelDB
import java.math.BigDecimal

class RestaurantFixture {
    class Pizzakaya(
            val id: Long = 1,
            val categories: List<CategoryModelDB> = listOf(Pizza().modelDb())
    ) {
        val name = "Pizzakaya"
        private val nameJp = "ピザカヤ"
        private val website = "pizzakaya.com"
        private val geolocation = Geolocation(BigDecimal.valueOf(35.662265), BigDecimal.valueOf(139.726658))
        fun model() = RestaurantModel(id, name, nameJp, website, geolocation, categories)
        fun modelDB() = RestaurantModelDB(id, name, nameJp, website, geolocation, categories.map { it.id })
        fun modelNew() = RestaurantModelNew(name, nameJp, website, geolocation, categories.map { it.id })
    }

    class Moti(
            val id: Long = 2,
            val categories: List<CategoryModelDB> = listOf(Curry().modelDb(), Spicy().modelDb())
    ) {
        val name = "Moti"
        private val nameJp = "モティ"
        private val website = null
        private val geolocation = null
        fun model() = RestaurantModel(id, name, nameJp, website, geolocation, categories)
        fun modelDB() = RestaurantModelDB(id, name, nameJp, website, geolocation, categories.map { it.id })
        fun modelNew() = RestaurantModelNew(name, nameJp, website, geolocation, categories.map { it.id })
    }

    class Pintokona(
            val id: Long = 3,
            val categories: List<CategoryModelDB> = listOf(Sushi().modelDb())
    ) {
        val name = "Pintokona"
        private val nameJp = "ぴんとこな"
        private val website = null
        private val geolocation = null
        fun modelDB() = RestaurantModelDB(id, name, nameJp, website, geolocation, categories.map { it.id })
        fun modelNew() = RestaurantModelNew(name, nameJp, website, geolocation, categories.map { it.id })
    }
}