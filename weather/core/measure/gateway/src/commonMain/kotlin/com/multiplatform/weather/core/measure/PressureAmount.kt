package com.multiplatform.weather.core.measure

data class PressureAmount(
    val amount: Double,
    val unit: Pressure,
) {

    companion object {

        fun of(amount: Double): PressureAmount =
            PressureAmount(amount, Pressure.MeanSeaLevel)
    }
}