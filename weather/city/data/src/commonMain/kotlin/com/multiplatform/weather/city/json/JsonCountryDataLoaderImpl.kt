package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.loader.JsonCountryDataLoader
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.buffer
import okio.use

internal class JsonCountryDataLoaderImpl(
    private val sourceProvider: JsonDataSourceProvider,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : JsonCountryDataLoader {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun invoke(input: String): Result<List<Country>> = runCatching {
        val source = sourceProvider(input)
        val countries = mutableListOf<Country>()
        source.buffer()
            .use { bufferedSource ->
                val jsonCountries = json.decodeFromBufferedSource<List<JsonCountry>>(bufferedSource)
                countries += jsonCountries.map { it.toDomain() }
            }
        countries
    }
}
