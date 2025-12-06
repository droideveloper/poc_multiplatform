package com.multiplatform.weather.city

import com.multiplatform.td.core.kotlin.KeyFactory

data class City(
    val id: Long,
    val name: String,
    val displayName: String,
    val adminName: String,
    val country: Country,
    val location: Location,
) : KeyFactory {

    companion object {
        val Defaults = City(
            id = 0,
            name = "",
            displayName = "",
            adminName = "",
            country = Country.Defaults,
            location = Location.Defaults,
        )
    }

    override fun createKey(): String = when {
        name != adminName -> "${name.lowercase()}_${adminName.lowercase()}_${country.name.lowercase()}"
        else -> "${name.lowercase()}_${country.name.lowercase()}"
    }

    val cityName: String = when {
        name != adminName -> "$name, $adminName"
        else -> name
    }
}
