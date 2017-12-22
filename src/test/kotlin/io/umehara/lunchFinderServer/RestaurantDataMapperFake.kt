package io.umehara.lunchFinderServer

class RestaurantDataMapperFake: RestaurantDataMapper {
    private val restaurants = listOf(
            Restaurant(1, "Pintokona"),
            Restaurant(2, "Momodori")
    )

    override fun all(): List<Restaurant> {
        return restaurants
    }
}