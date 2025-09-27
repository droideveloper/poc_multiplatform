package com.multiplatform.weather.onboarding.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.KeyedValueDataStoreException
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import okio.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

internal class OnboardingRepositoryImplTest {

    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey("onboarding") }

    @Test
    fun `given read from datastore than returns error result`() = runTest {
        val error = KeyedValueDataStoreException.NotFoundException("onboarding")
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getValue(dataStoreKey, Boolean::class)
            } returns Result.failure(error)
        }

        val repo = OnboardingRepositoryImpl(dataStore)
        val result = repo.get()

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            dataStore.getValue(dataStoreKey, Boolean::class)
        }
    }

    @Test
    fun `given read from datastore than returns success result`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getValue(dataStoreKey, Boolean::class)
            } returns Result.success(true)
        }

        val repo = OnboardingRepositoryImpl(dataStore)
        val result = repo.get()

        val actual = result.getOrThrow()
        assertTrue { actual }

        verifySuspend {
            dataStore.getValue(dataStoreKey, Boolean::class)
        }
    }

    @Test
    fun `given write from datastore than returns error result`() = runTest {
        val error = IOException("Not enough space")
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                setValue(dataStoreKey, false)
            } returns Result.failure(error)
        }

        val repo = OnboardingRepositoryImpl(dataStore)
        val result = repo.save(false)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            dataStore.setValue(dataStoreKey, false)
        }
    }

    @Test
    fun `given write from datastore than returns success result`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                setValue(dataStoreKey, false)
            } returns Result.success(Unit)
        }

        val repo = OnboardingRepositoryImpl(dataStore)
        val result = repo.save(false)

        val actual = result.getOrThrow()
        assertEquals(Unit, actual)

        verifySuspend {
            dataStore.setValue(dataStoreKey, false)
        }
    }
}
