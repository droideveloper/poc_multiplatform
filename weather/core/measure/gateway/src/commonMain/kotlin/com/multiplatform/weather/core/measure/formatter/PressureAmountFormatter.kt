package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.weather.core.measure.PressureAmount

interface PressureAmountFormatter {

    fun format(amount: PressureAmount): String
}