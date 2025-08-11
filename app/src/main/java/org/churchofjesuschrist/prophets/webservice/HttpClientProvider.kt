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

@Singleton
class HttpClientProvider @Inject constructor(
) {
    private val localCdnClient = HttpClient(OkHttp.create()) {
        install(Logging) { defaultSetup(LogLevel.ALL) }
        install(Resources)
        install(ContentNegotiation) { defaultSetup(allowAnyContentType = true) }
        defaultRequest {
            url("http://192.168.1.69:8000/api/")
            setupStandardHeaders()
        }
    }

    fun getCdnClient(): HttpClient = localCdnClient
}