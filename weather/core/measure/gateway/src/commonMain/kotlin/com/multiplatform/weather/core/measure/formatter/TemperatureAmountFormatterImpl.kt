package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import com.multiplatform.weather.core.measure.TemperatureAmount
import kotlin.math.roundToInt

@ContributesBinder(scope = AppScope::class)
internal class TemperatureAmountFormatterImpl : TemperatureAmountFormatter {

    override fun format(amount: TemperatureAmount): String {
        val (number, unit) = amount
        return "${number.roundToInt()} $unit"
    }
}