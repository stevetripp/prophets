package org.churchofjesuschrist.myfirstapp.webservice

import co.touchlab.kermit.Logger
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.http.isSuccess
import io.ktor.resources.Resource
import javax.inject.Inject
import javax.inject.Singleton
import org.churchofjesuschrist.myfirstapp.webservice.dto.ProphetDto

@Singleton
class LocalCdn @Inject constructor(
    httpClientProvider: HttpClientProvider,
) {
    private val httpClient = httpClientProvider.getCdnClient()

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

    @Resource("/prophets.json")
    object Prophets
}