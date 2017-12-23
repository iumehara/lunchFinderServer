package io.umehara.lunchFinderServer

class RestaurantDataMapperFakeTest: RestaurantDataMapperTest() {
    override fun setupRestaurantDataMapper(seedRestaurants: List<RestaurantModel>): RestaurantDataMapper {
        val restaurantDataMapperFake = RestaurantDataMapperFake()
        restaurantDataMapperFake.setSeedRestaurants(seedRestaurants)
        return restaurantDataMapperFake
    }
}
