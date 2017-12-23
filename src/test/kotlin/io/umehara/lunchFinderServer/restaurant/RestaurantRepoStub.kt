package io.umehara.lunchFinderServer.restaurant

class RestaurantRepoStub : RestaurantRepo {
    override fun all(): List<RestaurantModel> {
        return listOf(
                RestaurantModel(1, "Pintokona"),
                RestaurantModel(2, "Momodori")
        )
    }

    override fun get(id: Long): RestaurantModel {
        return RestaurantModel(1, "Pintokona")
    }

    var createArgument: RestaurantModelNew? = null
    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        createArgument = restaurantModelNew
        return 0L
    }
}