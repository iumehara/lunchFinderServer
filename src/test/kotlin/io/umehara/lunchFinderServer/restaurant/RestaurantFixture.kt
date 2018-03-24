package io.umehara.lunchFinderServer.restaurant

import io.umehara.lunchFinderServer.category.CategoryFixture.*
import io.umehara.lunchFinderServer.category.CategoryModelDB
import java.math.BigDecimal

class RestaurantFixture {
    class Pizzakaya(
            val id: Long = 1,
            val categories: List<CategoryModelDB> = listOf(Pizza().modelDb()),
            val categoryIds: List<Long> = listOf(Pizza().id)
    ) {
        val name = "Pizzakaya"
        private val nameJp = "ピザカヤ"
        private val website = "pizzakaya.com"
        private val geolocation = Geolocation(BigDecimal.valueOf(35.662265), BigDecimal.valueOf(139.726658))
        fun model() = RestaurantModel(id, name, nameJp, website, geolocation, categories)
        fun modelDB() = RestaurantModelDB(id, name, nameJp, website, geolocation, categoryIds)
        fun modelNew() = RestaurantModelNew(name, nameJp, website, geolocation, categoryIds)
    }

    class Moti(
            val id: Long = 2,
            val categoryIds: List<Long> = listOf(Curry().id, Spicy().id)
    ) {
        val name = "Moti"
        private val nameJp = "モティ"
        private val website = null
        private val geolocation = null
        fun modelDB() = RestaurantModelDB(id, name, nameJp, website, geolocation, categoryIds)
        fun modelNew() = RestaurantModelNew(name, nameJp, website, geolocation, categoryIds)
    }

    class Pintokona(
            val id: Long = 3,
            val categoryIds: List<Long> = listOf(Sushi().id)
    ) {
        val name = "Pintokona"
        private val nameJp = "ぴんとこな"
        private val website = null
        private val geolocation = null
        fun modelDB() = RestaurantModelDB(id, name, nameJp, website, geolocation, categoryIds)
        fun modelNew() = RestaurantModelNew(name, nameJp, website, geolocation, categoryIds)
    }
}