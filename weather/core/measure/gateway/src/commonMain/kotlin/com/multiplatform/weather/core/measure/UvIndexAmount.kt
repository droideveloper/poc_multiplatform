package com.multiplatform.weather.core.measure

data class UvIndexAmount(
    val amount: Double,
    val unit: UvIndex,
) {

    companion object {
        fun of(amount: Double) = UvIndexAmount(
            amount = amount,
            unit = UvIndex.Default,
        )
    }
}