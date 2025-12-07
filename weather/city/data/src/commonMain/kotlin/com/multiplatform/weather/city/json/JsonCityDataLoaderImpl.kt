package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.db.CityDao
import com.multiplatform.weather.city.loader.JsonCityDataLoader
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.buffer
import okio.use

internal class JsonCityDataLoaderImpl(
    private val dao: CityDao,
    private val sourceProvider: JsonDataSourceProvider,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : JsonCityDataLoader {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun invoke(
        input: CountryCode,
    ): Result<Unit> = runCatching {
        val code = input.value
        val hasAny = dao.any(code)
        if (hasAny.not()) {
            val source = sourceProvider(code.lowercase())
            source.buffer()
                .use { bufferedSource ->
                    val jsonCities = json.decodeFromBufferedSource<List<JsonCity>>(bufferedSource)
                    val cities = jsonCities.map { it.toData() }
                    dao.saveOrUpdate(cities)
                }
        }
    }.onFailure { it.printStackTrace() }
}
