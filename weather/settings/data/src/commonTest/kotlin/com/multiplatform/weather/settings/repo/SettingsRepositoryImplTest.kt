package com.multiplatform.weather.settings.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.KeyedValueDataStoreException
import com.multiplatform.weather.settings.Setting
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.json.JsonSettings
import com.multiplatform.weather.settings.json.toData
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okio.IOException
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class SettingsRepositoryImplTest {
    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey("settings") }

    @Test
    fun `given read from datastore than returns error result`() = runTest {
        val error = KeyedValueDataStoreException.NotFoundException("settings")
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getSerializable<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
            } returns Result.failure(error)
        }

        val repo = SettingsRepositoryImpl(dataStore)
        val result = repo.get()

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            dataStore.getSerializable<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
        }
    }

    @Test
    fun `given read from datastore than returns success result`() = runTest {
        val settings = Settings.Defaults
        val jsonSettings = settings.toData()

        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getSerializable<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
            } returns Result.success(jsonSettings)
        }

        val repo = SettingsRepositoryImpl(dataStore)
        val result = repo.get()

        val actual = result.getOrThrow()
        assertEquals(settings, actual)

        verifySuspend {
            dataStore.getSerializable<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
        }
    }

    @Test
    fun `given read from datastore asFlow than returns error result`() = runTest {
        val error = KeyedValueDataStoreException.NotFoundException("settings")
        val dataStore = mock<KeyedValueDataStore> {
            every {
                getSerializableFlow<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
            } returns flowOf(Result.failure(error))
        }

        val repo = SettingsRepositoryImpl(dataStore)
        repo.asFlow()
            .collect { result ->
                val actual = assertFails { result.getOrThrow() }
                assertEquals(error, actual)
            }

        verify {
            dataStore.getSerializableFlow<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
        }
    }

    @Test
    fun `given read from datastore asFlow than returns success result`() = runTest {
        val settings = Settings.Defaults
        val jsonSettings = settings.toData()

        val dataStore = mock<KeyedValueDataStore> {
            every {
                getSerializableFlow<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
            } returns flowOf(Result.success(jsonSettings))
        }

        val repo = SettingsRepositoryImpl(dataStore)
        repo.asFlow()
            .collect { result ->
                val actual =  result.getOrThrow()
                assertEquals(settings, actual)
            }

        verify {
            dataStore.getSerializableFlow<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
        }
    }


    @Test
    fun `given write from datastore than returns error result`() = runTest {
        val error = IOException("Not enough space")
        val jsonSettings = Settings.Defaults.toData()
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                setSerializable(dataStoreKey, typeOf<JsonSettings>(), jsonSettings)
            } returns Result.failure(error)
        }

        val repo = SettingsRepositoryImpl(dataStore)
        val result = repo.save(Settings.Defaults)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            dataStore.setSerializable(dataStoreKey, typeOf<JsonSettings>(), jsonSettings)
        }
    }

    @Test
    fun `given write from datastore than returns success result`() = runTest {
        val settings = Settings.Defaults
        val jsonSettings = settings.toData()

        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getSerializable<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
            } returns Result.success(jsonSettings)
        }

        val repo = SettingsRepositoryImpl(dataStore)
        val result = repo.get()

        val actual = result.getOrThrow()
        assertEquals(settings, actual)

        verifySuspend {
            dataStore.getSerializable<JsonSettings>(dataStoreKey, typeOf<JsonSettings>())
        }
    }

}
