package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.weather.core.measure.WindAmount

interface WindAmountFormatter {

    fun format(amount: WindAmount): String
}