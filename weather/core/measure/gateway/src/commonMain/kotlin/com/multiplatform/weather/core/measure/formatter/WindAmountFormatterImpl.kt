package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import com.multiplatform.weather.core.measure.WindAmount
import kotlin.math.roundToInt

@ContributesBinder(scope = AppScope::class)
internal class WindAmountFormatterImpl : WindAmountFormatter {

    override fun format(amount: WindAmount): String {
        val (number, unit) = amount
        return "${number.roundToInt()} $unit"
    }
}