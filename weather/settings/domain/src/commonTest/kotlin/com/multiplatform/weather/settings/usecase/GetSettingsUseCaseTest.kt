package com.multiplatform.weather.settings.usecase

import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.repo.SettingsRepository
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class GetSettingsUseCaseTest {

    @Test
    fun `given success will return settings`() = runTest {
        val repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.success(Settings.Defaults)
        }

        val useCase = GetSettingsUseCase(repository)

        val result = useCase()

        assertEquals(Settings.Defaults, result.getOrThrow())

        verifySuspend { repository.get() }
    }

    @Test
    fun `given failure will return error`() = runTest {
        val error = Exception()
        val repository = mock<SettingsRepository> {
            everySuspend { get() } throws error
        }

        val useCase = GetSettingsUseCase(repository)

        val result = useCase()

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend { repository.get() }
    }
}
