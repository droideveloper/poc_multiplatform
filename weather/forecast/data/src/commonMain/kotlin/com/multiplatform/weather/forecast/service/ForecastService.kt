package com.multiplatform.weather.forecast.service

import com.multiplatform.weather.forecast.ForecastDto
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

internal interface ForecastService {

    // Ktorfit does encode on `,`, but API apparently does not like it
    @GET(
        "v1/forecast" +
            "?hourly=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m,pressure_msl" +
            "&daily=sunset,sunrise,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,wind_speed_10m_max,wind_speed_10m_min,uv_index_max",
    )
    suspend fun forecast(
        @Query(
            value = "latitude",
            encoded = false,
        ) latitude: Double,
        @Query(
            value = "longitude",
            encoded = false,
        ) longitude: Double,
        @Query(
            value = "forecast_days",
            encoded = false,
        ) days: Int = 10,
    ): Result<ForecastDto>
}

internal object MockService {

    fun create(): ForecastService = object : ForecastService {

        override suspend fun forecast(
            latitude: Double,
            longitude: Double,
            days: Int,
        ): Result<ForecastDto> = Result.failure(Throwable())
    }
}
