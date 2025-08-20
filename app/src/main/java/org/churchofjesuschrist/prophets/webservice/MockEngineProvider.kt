package org.churchofjesuschrist.prophets.webservice

import android.app.Application
import co.touchlab.kermit.Logger
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import javax.inject.Inject
import javax.inject.Singleton
import okio.Path.Companion.toPath
import okio.assetfilesystem.asFileSystem

/**
 * Provides a mock HTTP engine for testing network requests using local asset files.
 *
 * This class uses the application's assets to serve predefined responses for specific API endpoints.
 * If a request matches a mocked endpoint, the corresponding asset file is returned as the response.
 * Otherwise, a bad request response is returned.
 *
 * @property mockEngine The engine that intercepts HTTP requests and serves mock responses from assets.
 * @constructor Injects the [Application] to access asset files.
 */
@Singleton
class MockEngineProvider @Inject constructor(
    /**
     * The application instance used to access asset files for mocking responses.
     */
    application: Application
) {
    private val fileSystem = application.assets.asFileSystem()

    /**
     * Mock engine that intercepts HTTP requests and serves responses from asset files.
     *
     * If the request URL matches [API_PROPHETS_JSON], the corresponding asset file is returned.
     * Otherwise, a warning is logged and a bad request response is returned.
     */
    val mockEngine = MockEngine { httpRequestData/*: HttpRequestData */ ->
        val urlPath = httpRequestData.url.segments.joinToString("/")
        val content = when (urlPath) {
            API_PROPHETS_JSON -> fileSystem.read(API_PROPHETS_JSON.toPath()) { readUtf8() }
            else -> {
                Logger.w { "$urlPath is not mocked." }
                null
            }
        }

        content?.let {
            respond(content = content, headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()), status = HttpStatusCode.OK)
        } ?: respondBadRequest()
    }

    companion object {
        /**
         * Path to the mocked prophets API response asset file.
         */
        private const val API_PROPHETS_JSON = "api/prophets.json"
    }
}