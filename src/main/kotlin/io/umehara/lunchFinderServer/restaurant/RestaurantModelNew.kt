package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelNew(
        var name: String = "",
        var categoryIds: List<Long> = ArrayList()
)