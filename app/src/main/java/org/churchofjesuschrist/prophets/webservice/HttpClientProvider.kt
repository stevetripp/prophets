package org.churchofjesuschrist.prophets.webservice

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import javax.inject.Inject
import javax.inject.Singleton
import org.churchofjesuschrist.prophets.webservice.KtorClientDefaults.defaultSetup
import org.churchofjesuschrist.prophets.webservice.KtorClientDefaults.setupStandardHeaders

/**
 * HttpClientProvider is a singleton class responsible for providing configured instances of [HttpClient]
 * for accessing the local CDN and for mocking CDN requests in tests.
 *
 * It sets up HTTP clients with logging, resource handling, content negotiation, and standard headers.
 *
 * @property mockEngineProvider Provider for the mock engine used in testing.
 * @constructor Injects a [MockEngineProvider] for test client setup.
 */
@Singleton
class HttpClientProvider @Inject constructor(
    mockEngineProvider: MockEngineProvider,
) {
    /**
     * HTTP client configured for accessing the CDN.
     * Uses OkHttp engine, logging, resources, content negotiation, and standard headers.
     */
    private val cdnClient = HttpClient(OkHttp.create()) {
        install(Logging) { defaultSetup(LogLevel.ALL) }
        install(Resources)
        install(ContentNegotiation) { defaultSetup(allowAnyContentType = true) }
        defaultRequest {
            url("http://192.168.1.69:8000/api/")
            setupStandardHeaders()
        }
    }

    /**
     * HTTP client configured for mocking CDN requests in tests.
     * Uses a mock engine, logging, resources, content negotiation, and standard headers.
     */
    private val mockCdnClient = HttpClient(mockEngineProvider.mockEngine) {
        install(Logging) { defaultSetup(LogLevel.ALL) }
        install(Resources)
        install(ContentNegotiation) { defaultSetup(allowAnyContentType = true) }
        defaultRequest {
            setupStandardHeaders()
        }
    }

    /**
     * Returns the HTTP client configured for mocking CDN requests.
     *
     * @return [HttpClient] instance for mock CDN access.
     */
    fun getCdnClient(): HttpClient = mockCdnClient // Replace with `cdnClient` to interact with the actual CDN
}