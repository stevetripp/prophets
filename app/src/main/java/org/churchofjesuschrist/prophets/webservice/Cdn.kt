package org.churchofjesuschrist.prophets.webservice

import co.touchlab.kermit.Logger
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.http.isSuccess
import io.ktor.resources.Resource
import javax.inject.Inject
import javax.inject.Singleton
import org.churchofjesuschrist.prophets.webservice.dto.ProphetDto

/**
 * Cdn is a singleton service class responsible for fetching data from the local CDN.
 *
 * This class provides methods to interact with the CDN, such as retrieving a list of prophets.
 * It uses an injected [HttpClientProvider] to obtain a configured HTTP client for CDN requests.
 *
 * @constructor Injects an [HttpClientProvider] to create the HTTP client for CDN access.
 */
@Singleton
class Cdn @Inject constructor(
    httpClientProvider: HttpClientProvider,
) {
    private val httpClient = httpClientProvider.getCdnClient()

    /**
     * Fetches the list of prophets from the CDN.
     *
     * Makes an HTTP GET request to the Prophets endpoint and returns the result.
     * On success, returns [Result.Success] containing a list of [ProphetDto].
     * On failure (HTTP error or exception), returns [Result.Error] with the error details.
     *
     * @return [Result] containing either a list of [ProphetDto] on success, or an error on failure.
     */
    suspend fun getProphets(): Result<List<ProphetDto>> {
        return try {
            val response = httpClient.get(Prophets)
            if (response.status.isSuccess()) {
                val body = response.body<List<ProphetDto>>()
                Result.Success(body)
            } else {
                Result.Error(Exception("HTTP error: ${response.status}"))
            }
        } catch (e: Exception) {
            Logger.e(e) { "Failed to fetch prophets from local CDN" }
            Result.Error(e)
        }
    }

    @Resource("/api/prophets.json")
    object Prophets
}