package com.multiplatform.weather.city

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        val Defaults = Location(
            latitude = Double.NaN,
            longitude = Double.NaN,
        )
    }
}