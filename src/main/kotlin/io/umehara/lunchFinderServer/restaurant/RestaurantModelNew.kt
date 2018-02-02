package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelNew(
        val name: String = "",
        val nameJp: String = "",
        val website: String? = null,
        val geolocation: Geolocation? = null,
        var categoryIds: List<Long> = ArrayList()
)
