package com.multiplatform.td.core.network.client

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.NetworkScope
import com.multiplatform.td.core.log.PlatformLogger
import com.multiplatform.td.core.network.Url
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.Charsets
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.withCharset
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.charsets.Charsets
import kotlinx.serialization.json.Json

internal typealias KtorLogger = io.ktor.client.plugins.logging.Logger

@ContributesBinder(scope = NetworkScope::class)
internal class ClientConfigHttpImpl(
    private val url: Url,
    private val environment: Environment,
    private val platformLogger: PlatformLogger,
) : ClientConfigHttp {

    override fun invoke(arg: HttpClientConfig<*>) {
        val urlString = url.value
        arg.apply {

            install(Logging) {
                level = if (environment.isDebug) LogLevel.ALL else LogLevel.NONE
                logger = object : KtorLogger {
                    override fun log(message: String) {
                        platformLogger.log(tag = "Network", message = message)
                    }
                }
            }

            defaultRequest {
                contentType(
                    ContentType.Application.Json
                        .withCharset(Charsets.UTF_8)
                )

                url(urlString)

                headers.append(HttpHeaders.Connection, "keep-alive")
            }

            install(UserAgent) { agent = environment.platform.toString() }

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            Charsets { register(Charsets.UTF_8) }
        }
    }
}
