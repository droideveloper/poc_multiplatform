package com.multiplatform.weather.city

data class Country(
    val name: String,
    val code: CountryCode,
) {
    companion object {
        val Defaults = Country(
            name = "Turkey",
            code = CountryCode.getOrThrow("TR"),
        )
    }
}
