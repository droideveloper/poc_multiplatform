package com.multiplatform.weather.core.measure

data class TemperatureAmount(
    val amount: Double,
    val unit: Temperature,
) {
    companion object {
        fun celsius(amount: Double): TemperatureAmount =
            TemperatureAmount(amount, Temperature.Celsius)

        fun fahrenheit(amount: Double): TemperatureAmount =
            TemperatureAmount(amount, Temperature.Fahrenheit)
    }
}

fun TemperatureAmount.toCelsius(): TemperatureAmount {
    if (unit == Temperature.Celsius) return this
    return TemperatureAmount(
        amount = (amount - 32) * 1.8,
        unit = Temperature.Celsius,
    )
}
fun TemperatureAmount.toFahrenheit(): TemperatureAmount {
    if (unit == Temperature.Fahrenheit) return this
    return TemperatureAmount(
        amount = (amount * 1.8) + 32,
        unit = Temperature.Fahrenheit,
    )
}