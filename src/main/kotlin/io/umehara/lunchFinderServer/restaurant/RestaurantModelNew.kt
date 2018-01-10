package io.umehara.lunchFinderServer.restaurant

data class RestaurantModelNew(
        val name: String = "",
        val nameJp: String = "",
        var categoryIds: List<Long> = ArrayList()
) {
}