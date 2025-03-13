package com.multiplatform.weather.core.measure.formatter

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import com.multiplatform.weather.core.measure.HumidityAmount

@ContributesBinder(scope = AppScope::class)
internal class HumidityAmountFormatterImpl : HumidityAmountFormatter {

    override fun format(amount: HumidityAmount): String {
        val (number, unit) = amount
        return "$number $unit"
    }
}