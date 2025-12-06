package com.multiplatform.weather

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class WeatherTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application? {
        val clazzName = TestWeatherApplication::class.java.name
        return super.newApplication(cl, clazzName, context)
    }
}
