package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.db.CityDao
import com.multiplatform.weather.city.loader.JsonDataLoader
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.buffer
import okio.use

internal class JsonDataLoaderImpl(
    private val dao: CityDao,
    private val sourceProvider: JsonDataSourceProvider,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : JsonDataLoader {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun invoke(): Result<Unit> = runCatching {
        val hasAny = dao.any()
        if (hasAny.not()) {
            val source = sourceProvider()
            source.buffer()
                .use { bufferedSource ->
                    val jsonCities = json.decodeFromBufferedSource<List<JsonCity>>(bufferedSource)
                    val cities = jsonCities.map { it.toData() }
                    dao.saveOrUpdate(cities)
                }
        }
    }
}
