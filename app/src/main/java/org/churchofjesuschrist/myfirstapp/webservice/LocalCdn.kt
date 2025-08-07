package org.churchofjesuschrist.myfirstapp.webservice

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.http.isSuccess
import io.ktor.resources.Resource
import javax.inject.Inject
import javax.inject.Singleton
import org.churchofjesuschrist.myfirstapp.webservice.dto.ProphetDto

@Singleton
class LocalCdn @Inject constructor(
    private val httpClientProvider: HttpClientProvider,
) {
    private val httpClient = httpClientProvider.getCdnClient()

    suspend fun getProphets(): List<ProphetDto> {
        println("[LocalCdn] getProphets called")
        val response = httpClient.get(Prophets)
        println("[LocalCdn] Response status: ${response.status}")
        return if (response.status.isSuccess()) {
            val body = response.body<List<ProphetDto>>()
            println("[LocalCdn] Received prophets: $body")
            body
        } else {
            println("[LocalCdn] Failed to fetch prophets, returning empty list")
            emptyList()
        }
    }

    @Resource("/prophets.json")
    object Prophets
}