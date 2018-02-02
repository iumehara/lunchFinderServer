package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelDB(
        val id: Long = 0,
        val name: String = "",
        val nameJp: String = "",
        val website: String? = null,
        val geolocation: Geolocation? = null,
        val categoryIds: List<Long> = ArrayList()
)
