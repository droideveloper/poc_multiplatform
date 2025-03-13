package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.weather.core.measure.TemperatureAmount

interface TemperatureAmountFormatter {

    fun format(amount: TemperatureAmount): String
}