package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelDB(
        var id: Long = 0,
        var name: String = "",
        var categoryIds: List<Long> = ArrayList()
)