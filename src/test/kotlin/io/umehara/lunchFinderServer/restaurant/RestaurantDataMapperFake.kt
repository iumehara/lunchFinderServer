package io.umehara.lunchFinderServer.restaurant

class RestaurantDataMapperFake: RestaurantDataMapper {
    private var restaurants = mutableListOf<RestaurantModelDB>()
    fun setSeedRestaurants(seedRestaurantDBS: List<RestaurantModelDB>) {
        restaurants.addAll(seedRestaurantDBS)
    }

    fun resetSeedRestaurants() {
        restaurants.clear()
    }

    override fun all(): List<RestaurantModelDB> {
        return restaurants.sortedWith(compareBy({it.name}))
    }

    override fun allByCategoryId(categoryId: Long): List<RestaurantModelDB> {
        return restaurants
                .filter { it.categoryIds.contains(categoryId) }
                .toMutableList()
                .sortedWith(compareBy({it.name}))
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
                restaurantModelNew.nameJp,
                restaurantModelNew.website,
                restaurantModelNew.geolocation,
                restaurantModelNew.categoryIds
        )
        restaurants.add(newRestaurant)
        return id
    }

    override fun update(id: Long, restaurantModelNew: RestaurantModelNew) {
        restaurants = restaurants
                .map {
                    if (it.id == id) {
                        RestaurantModelDB(
                                it.id,
                                restaurantModelNew.name,
                                restaurantModelNew.nameJp,
                                restaurantModelNew.website ?: it.website,
                                restaurantModelNew.geolocation ?: it.geolocation,
                                restaurantModelNew.categoryIds
                        )
                    } else {
                        it
                    }
                }.toMutableList()
    }

    override fun addCategory(id: Long, categoryId: Long) {
        val restaurant = get(id)
        val categoryIds = restaurant.categoryIds.toMutableList()
        categoryIds.add(categoryId)

        val restaurantModelNew = RestaurantModelNew(
                restaurant.name,
                restaurant.nameJp,
                restaurant.website,
                restaurant.geolocation,
                categoryIds
        )

        update(id, restaurantModelNew)
    }

    override fun removeCategory(id: Long, categoryId: Long) {
        val restaurant = get(id)
        val categoryIds = restaurant.categoryIds.toMutableList()
        categoryIds.remove(categoryId)

        val restaurantModelNew = RestaurantModelNew(
                restaurant.name,
                restaurant.nameJp,
                restaurant.website,
                restaurant.geolocation,
                categoryIds
        )

        update(id, restaurantModelNew)
    }

    override fun destroy(id: Long) {
        val filteredRestaurants = restaurants.filter { it.id != id }
        restaurants = filteredRestaurants as MutableList<RestaurantModelDB>
    }
}
