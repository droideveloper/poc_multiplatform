package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.db.CityDao
import com.multiplatform.weather.city.db.CityDto
import com.multiplatform.weather.city.db.toDomain
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CityRepositoryImplTest {

    private val cityDto = CityDto(
        id = 1,
        name = "Istanbul",
        asciiName = "Istanbul",
        countryName = "Turkey",
        countryCode = "TR",
        latitude = 41.0136,
        longitude = 28.955,
    )

    private val city by lazy { cityDto.toDomain() }

    private val dao = mock<CityDao> {
        everySuspend { saveOrUpdate(cityDto) } returns Unit
        everySuspend { saveOrUpdate(listOf(cityDto)) } returns Unit
        everySuspend { cities() } returns listOf(cityDto)
        everySuspend { any() } returns true
        everySuspend { delete(cityDto) } returns Unit
        everySuspend { delete(listOf(cityDto)) } returns Unit
    }

    @Test
    fun `given saveOrUpdate than will save city`() = runTest {
        val repo = CityRepositoryImpl(dao)

        val result = repo.saveOrUpdate(city)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend { dao.saveOrUpdate(cityDto) }
    }

    @Test
    fun `given saveOrUpdate than will save cities`() = runTest {
        val repo = CityRepositoryImpl(dao)

        val result = repo.saveOrUpdate(listOf(city))

        assertEquals(Unit, result.getOrThrow())

        verifySuspend { dao.saveOrUpdate(listOf(cityDto)) }
    }

    @Test
    fun `given cities than will return cities`() = runTest {
        val repo = CityRepositoryImpl(dao)

        val result = repo.cities()

        assertContains(result.getOrThrow(), city)

        verifySuspend { dao.cities() }
    }

    @Test
    fun `given any than will return true`() = runTest {
        val repo = CityRepositoryImpl(dao)

        val result = repo.any()

        assertTrue { result.getOrThrow() }

        verifySuspend { dao.any() }
    }

    @Test
    fun `given delete than will delete city`() = runTest {
        val repo = CityRepositoryImpl(dao)

        val result = repo.delete(city)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend { dao.delete(cityDto) }
    }

    @Test
    fun `given delete than will delete cities`() = runTest {
        val repo = CityRepositoryImpl(dao)

        val result = repo.delete(listOf(city))

        assertEquals(Unit, result.getOrThrow())

        verifySuspend { dao.delete(listOf(cityDto)) }
    }
}
