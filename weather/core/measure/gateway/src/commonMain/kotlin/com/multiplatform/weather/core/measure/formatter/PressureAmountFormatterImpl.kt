package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import com.multiplatform.weather.core.measure.PressureAmount
import kotlin.math.roundToInt

@ContributesBinder(scope = AppScope::class)
internal class PressureAmountFormatterImpl : PressureAmountFormatter {

    override fun format(amount: PressureAmount): String {
        val (number, unit) = amount
        return "${number.roundToInt()} $unit"
    }
}