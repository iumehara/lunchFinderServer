package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelNew(
        val name: String = "",
        val nameJp: String = "",
        val website: String? = null,
        val geoLocation: GeoLocation? = null,
        var categoryIds: List<Long> = ArrayList()
)
