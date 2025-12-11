package com.multiplatform.weather.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.multiplatform.weather.MainActivity
import com.multiplatform.weather.core.test.UserState
import com.multiplatform.weather.framework.WeatherTest
import com.multiplatform.weather.framework.createWeatherTest
import com.multiplatform.weather.test.robot.forecast.onForecast
import com.multiplatform.weather.test.robot.nextdays.onNextDays
import com.multiplatform.weather.test.robot.onboarding.onCitySelection
import com.multiplatform.weather.test.robot.onboarding.onTemperatureSelection
import com.multiplatform.weather.test.robot.onboarding.onWindSpeedSelection
import com.multiplatform.weather.test.robot.settings.onSettings
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class OnboardingFlowTest : WeatherTest<MainActivity> by createWeatherTest() {

    internal object DefaultUserState : UserState {
        override fun setup() = Unit
    }

    @Test
    fun testUserNotOnboardedYet() {
        launchForUserState(DefaultUserState) {
            onCitySelection {
                titleAndMessageDisplayed()
                cityTextDisplayed()
                selectCity()
                clickContinue()
            }
            onTemperatureSelection {
                titleAndMessageDisplayed()
                celsiusSelected()
                clickContinue()
            }
            onWindSpeedSelection {
                titleAndMessageDisplayed()
                kmhSelected()
                clickDone()
            }
            onForecast {
                dateCityDisplayed()
                weatherDetailsDisplayed()
                clickSetting()
            }
            onSettings {
                temperatureUnitsDisplayed()
                windSpeedUnitsDisplayed()
                numberOfDaysDisplayed()
                versionDisplayed()
                clickBack()
            }
            onForecast {
                clickNext10Days()
            }
            onNextDays {
                weatherDetailsDisplayed()
                nextDaysFirstDisplayed()
                nextDaysLastDisplayed()
                clickBack()
            }
        }
    }
}
