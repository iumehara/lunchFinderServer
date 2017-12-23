package io.umehara.lunchFinderServer

class RestaurantRepoStub : RestaurantRepo {
    var createArgument: RestaurantModelNew? = null
    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        createArgument = restaurantModelNew
        return 0L
    }

    override fun all(): List<RestaurantModel> {
        return listOf(
                RestaurantModel(1, "Pintokona"),
                RestaurantModel(2, "Momodori")
        )
    }
}
