package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.weather.core.measure.HumidityAmount

interface HumidityAmountFormatter {

    fun format(amount: HumidityAmount): String
}