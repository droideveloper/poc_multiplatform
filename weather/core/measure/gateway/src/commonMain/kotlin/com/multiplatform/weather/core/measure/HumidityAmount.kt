package com.multiplatform.weather.core.measure

data class HumidityAmount(
    val amount: Int,
    val unit : Humidity,
) {

    companion object {
        fun of(amount: Int): HumidityAmount =
            HumidityAmount(amount, Humidity.Percentage)
    }
}