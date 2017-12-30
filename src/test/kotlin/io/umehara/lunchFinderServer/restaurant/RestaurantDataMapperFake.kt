package io.umehara.lunchFinderServer.restaurant

class RestaurantDataMapperFake: RestaurantDataMapper {
    private var restaurants = mutableListOf<RestaurantModelDB>()

    fun setSeedRestaurants(seedRestaurantDBS: List<RestaurantModelDB>) {
        restaurants.addAll(seedRestaurantDBS)
    }

    override fun all(): List<RestaurantModelDB> {
        return restaurants
    }

    override fun get(id: Long): RestaurantModelDB {
        val filteredRestaurants = restaurants.filter { it.id == id }
        return filteredRestaurants[0]
    }

    override fun create(restaurantModelNew: RestaurantModelNew): Long {
        val id = restaurants.size + 1L
        val newRestaurant = RestaurantModelDB(
                id,
                restaurantModelNew.name,
                restaurantModelNew.categoryIds
        )
        restaurants.add(newRestaurant)
        return id
    }

    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        restaurants = restaurants
                .map {
                    if (it.id == id) {
                        RestaurantModelDB(it.id, restaurantModelNew.name, restaurantModelNew.categoryIds)
                    } else {
                        it
                    }
                }.toMutableList()
    }
}
