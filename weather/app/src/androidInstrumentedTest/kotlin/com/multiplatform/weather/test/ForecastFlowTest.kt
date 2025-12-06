package com.multiplatform.weather.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.weather.MainActivity
import com.multiplatform.weather.core.test.UserState
import com.multiplatform.weather.framework.WeatherTest
import com.multiplatform.weather.framework.createWeatherTest
import com.multiplatform.weather.test.robot.forecast.onForecast
import com.multiplatform.weather.test.robot.nextdays.onNextDays
import com.multiplatform.weather.test.robot.settings.onSettings
import com.multiplatform.weather.testComponent
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ForecastFlowTest : WeatherTest<MainActivity> by createWeatherTest() {

    internal object DefaultUserState : UserState {

        val dataStore: KeyedValueDataStore get() {
            return testComponent.dataStore
        }

        override fun setup() {
            runBlocking {
                println("dataStore test : $dataStore")

                val onboardingKey = DataStoreKey.defaultStoreKey("onboarding")
                dataStore.setValue(onboardingKey, true)
                    .getOrThrow()

                val selectedCityKey = DataStoreKey.defaultStoreKey("selected_cities")
                val json = "[{\"id\":1,\"city\":\"Istanbul\",\"city_ascii\":\"Istanbul\",\"lat\":41.0136,\"lng\":28.955,\"country\":\"Turkey\",\"iso2\":\"TR\",\"admin_name\":\"Istanbul\"}]"
                dataStore.setValue(selectedCityKey, json)
                    .getOrThrow()
            }
        }
    }

    @Test
    fun testOnboardedUser() {
        launchForUserState(DefaultUserState) {
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
