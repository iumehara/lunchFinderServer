package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelDB(
        val id: Long = 0,
        val name: String = "",
        val nameJp: String = "",
        val categoryIds: List<Long> = ArrayList()
)
