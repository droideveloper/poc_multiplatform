package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.loader.JsonCityDataLoader
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class PopulateDatabaseUseCaseTest {

    private val countryCode = CountryCode.getOrThrow("TR")

    @Test
    fun `given success than will return Unit`() = runTest {
        val jsonDataLoader = mock<JsonCityDataLoader> {
            everySuspend { invoke(countryCode) } returns Result.success(Unit)
        }

        val useCase = PopulateDatabaseUseCase(jsonDataLoader)
        val result = useCase(countryCode)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend { jsonDataLoader(countryCode) }
    }

    @Test
    fun `given failure than will return error`() = runTest {
        val error = Throwable()
        val jsonDataLoader = mock<JsonCityDataLoader> {
            everySuspend { invoke(countryCode) } returns Result.failure(error)
        }

        val useCase = PopulateDatabaseUseCase(jsonDataLoader)
        val result = useCase(countryCode)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend { jsonDataLoader(countryCode) }
    }
}
