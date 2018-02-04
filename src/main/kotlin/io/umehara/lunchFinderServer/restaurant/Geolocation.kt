package io.umehara.lunchFinderServer.restaurant

import java.math.BigDecimal

data class Geolocation(
        val lat: BigDecimal = BigDecimal.valueOf(0L),
        val long: BigDecimal = BigDecimal.valueOf(0L)
)

class GeolocationFactory {
    fun init(lat: BigDecimal?, long: BigDecimal?): Geolocation? {
        return if (lat != null && long != null) {
            Geolocation(lat, long)
        } else {
            null
        }
    }
}