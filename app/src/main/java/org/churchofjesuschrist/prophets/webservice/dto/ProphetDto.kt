package org.churchofjesuschrist.prophets.webservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProphetDto(
    val prophetCalled: String? = null,
    val born: String? = null,
    val apostleCalled: String? = null,
    val notableQuotes: List<String> = emptyList(),
    val name: String,
    val died: String? = null,
    val imageUrl: String,
)
