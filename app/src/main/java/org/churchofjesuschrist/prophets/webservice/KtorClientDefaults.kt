package org.churchofjesuschrist.prophets.webservice

import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.LoggingConfig
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Project Specific Defaults (should not be in shared library)
 */
object KtorClientDefaults {
    fun LoggingConfig.defaultSetup(logLevel: LogLevel = LogLevel.INFO) {
        logger = object : Logger {
            override fun log(message: String) {
                co.touchlab.kermit.Logger.i { message }
            }
        }
        level = logLevel
    }

    fun ContentNegotiationConfig.defaultSetup(
        allowAnyContentType: Boolean = false,
        jsonPrettyPrint: Boolean = false
    ) {
        val jsonDefinition = Json {
            prettyPrint = jsonPrettyPrint
            isLenient = true
            coerceInputValues = true // incorrect JSON values to the default
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

        json(jsonDefinition)

        // work-around for github that does NOT return Content-Type: application/json (github returns  Content-Type: text/plain; charset=utf-8)
        if (allowAnyContentType) {
            register(ContentType.Any, KotlinxSerializationConverter(jsonDefinition))
        }
    }

    fun DefaultRequest.DefaultRequestBuilder.setupStandardHeaders() {
        accept(ContentType.Application.Json)
        contentType(ContentType.Application.Json)
        headers.append("client-app-name", "my-first-app")
    }
}