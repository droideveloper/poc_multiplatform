package com.multiplatform.weather.city

import com.multiplatform.td.core.kotlin.KeyFactory

data class City(
    val id: Long,
    val name: String,
    val displayName: String,
    val country: Country,
    val location: Location,
) : KeyFactory {

    companion object {
        val Defaults = City(
            id = 0,
            name = "",
            displayName = "",
            country = Country.Defaults,
            location = Location.Defaults,
        )
    }

    override fun createKey(): String {
        return "${name.lowercase()}_${country.name.lowercase()}"
    }
}
