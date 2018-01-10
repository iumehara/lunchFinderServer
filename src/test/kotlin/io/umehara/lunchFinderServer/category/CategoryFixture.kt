package io.umehara.lunchFinderServer.category

class CategoryFixture {
    class Pizza(val id: Long = 1) {
        val name: String = "Pizza"
        fun modelDb() = CategoryModelDB(id, name)
    }

    class Spicy(val id: Long = 2) {
        val name: String = "Spicy"
        fun modelDb() = CategoryModelDB(id, name)
    }

    class Curry(val id: Long = 3) {
        val name: String = "Curry"
        fun modelDb() = CategoryModelDB(id, name)
    }

    class Sushi(val id: Long = 4) {
        val name: String = "Sushi"
        fun modelDb() = CategoryModelDB(id, name)
    }
}