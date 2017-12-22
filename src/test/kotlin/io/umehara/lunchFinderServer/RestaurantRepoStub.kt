package io.umehara.lunchFinderServer

class RestaurantRepoStub : RestaurantRepo {
    override fun all(): List<Restaurant> {
        return listOf(
                Restaurant(1, "Pintokona"),
                Restaurant(2, "Momodori")
        )
    }

}
