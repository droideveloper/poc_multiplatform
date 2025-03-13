package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import com.multiplatform.weather.core.measure.UvIndexAmount
import kotlin.math.roundToInt

@ContributesBinder(scope = AppScope::class)
internal class UvIndexAmountFormatterImpl : UvIndexAmountFormatter {

    override fun format(amount: UvIndexAmount): String {
        val (number, unit) = amount
        return "${number.roundToInt()} $unit"
    }
}