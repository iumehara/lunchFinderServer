package io.umehara.lunchFinderServer.restaurant

import java.math.BigDecimal

data class GeoLocation(
        val lat: BigDecimal = BigDecimal.valueOf(0L),
        val long: BigDecimal = BigDecimal.valueOf(0L)
)

class GeoLocationFactory {
    fun init(lat: BigDecimal?, long: BigDecimal?): GeoLocation? {
        return if (lat != null && long != null) {
            GeoLocation(lat, long)
        } else {
            null
        }
    }
}