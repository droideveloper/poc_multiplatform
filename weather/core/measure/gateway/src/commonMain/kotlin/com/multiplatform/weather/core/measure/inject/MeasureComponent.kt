package com.multiplatform.weather.core.measure.inject

import com.multiplatform.td.core.injection.scopes.AppScope
import com.multiplatform.weather.core.measure.formatter.GeneratedBinderModule
import com.multiplatform.weather.core.measure.formatter.HumidityAmountFormatter
import com.multiplatform.weather.core.measure.formatter.PressureAmountFormatter
import com.multiplatform.weather.core.measure.formatter.TemperatureAmountFormatter
import com.multiplatform.weather.core.measure.formatter.UvIndexAmountFormatter
import com.multiplatform.weather.core.measure.formatter.WindAmountFormatter
import me.tatarka.inject.annotations.Component

@AppScope
@Component
abstract class MeasureComponent : GeneratedBinderModule {
    companion object;

    abstract val humidityAmountFormatter: HumidityAmountFormatter
    abstract val pressureAmountFormatter: PressureAmountFormatter
    abstract val temperatureAmountFormatter: TemperatureAmountFormatter
    abstract val uvIndexAmountFormatter: UvIndexAmountFormatter
    abstract val windAmountFormatter: WindAmountFormatter
}