package io.umehara.lunchFinderServer.category

class CategoryFixture {
    class Pizza(
            val id: Long = 1,
            val restaurantCount: Long = 0
    ) {
        val name: String = "Pizza"
        fun modelDb() = CategoryModelDB(id, name, restaurantCount)
        fun modelNew() = CategoryModelNew(name)
    }

    class Spicy(
            val id: Long = 2,
            private val restaurantCount: Long = 0
    ) {
        val name: String = "Spicy"
        fun modelDb() = CategoryModelDB(id, name, restaurantCount)
        fun modelNew() = CategoryModelNew(name)
    }

    class Curry(
            val id: Long = 3,
            val restaurantCount: Long = 0
    ) {
        val name: String = "Curry"
        fun modelDb() = CategoryModelDB(id, name, restaurantCount)
        fun modelNew() = CategoryModelNew(name)
    }

    class Sushi(
            val id: Long = 4,
            val restaurantCount: Long = 0
    ) {
        val name: String = "Sushi"
        fun modelDb() = CategoryModelDB(id, name, restaurantCount)
        fun modelNew() = CategoryModelNew(name)
    }
}