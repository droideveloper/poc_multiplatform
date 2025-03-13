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

internal class SaveSettingsUseCaseTest {

    @Test
    fun `given success will save settings`() = runTest {
        val repository = mock<SettingsRepository> {
            everySuspend { save(Settings.Defaults) } returns Result.success(Unit)
        }

        val useCase = SaveSettingsUseCase(repository)

        val result = useCase(Settings.Defaults)
        assertEquals(Unit, result.getOrThrow())

        verifySuspend { repository.save(Settings.Defaults) }
    }

    @Test
    fun `given failure will return error`() = runTest {
        val error = Exception()
        val repository = mock<SettingsRepository> {
            everySuspend { save(Settings.Defaults) } throws error
        }

        val useCase = SaveSettingsUseCase(repository)

        val result = useCase(Settings.Defaults)
        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend { repository.save(Settings.Defaults) }
    }
}