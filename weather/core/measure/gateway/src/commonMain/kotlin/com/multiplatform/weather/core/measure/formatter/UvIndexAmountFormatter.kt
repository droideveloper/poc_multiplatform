package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.weather.core.measure.UvIndexAmount

interface UvIndexAmountFormatter {

    fun format(amount: UvIndexAmount): String
}