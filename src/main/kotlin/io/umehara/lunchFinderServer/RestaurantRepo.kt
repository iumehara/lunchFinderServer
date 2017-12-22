package io.umehara.lunchFinderServer

interface RestaurantRepo {
    fun all(): List<Restaurant>
}