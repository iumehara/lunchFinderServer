package io.umehara.lunchFinderServer.restaurant

class RestaurantDataMapperFakeTest: RestaurantDataMapperTest() {
    override fun setupRestaurantDataMapper(seedRestaurantDBS: List<RestaurantModelDB>): RestaurantDataMapper {
        val restaurantDataMapperFake = RestaurantDataMapperFake()
        restaurantDataMapperFake.setSeedRestaurants(seedRestaurantDBS)
        return restaurantDataMapperFake
    }
}
