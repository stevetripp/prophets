package org.churchofjesuschrist.prophets.util

import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity
import org.churchofjesuschrist.prophets.webservice.dto.ProphetDto

fun ProphetEntity.toDto(): ProphetDto = ProphetDto(
    prophetCalled = this.prophetCalled,
    born = this.born,
    apostleCalled = this.apostleCalled,
    notableQuotes = this.notableQuotes,
    name = this.name,
    died = this.died,
    imageUrl = this.imageUrl
)

fun ProphetDto.toEntity(): ProphetEntity = ProphetEntity(
    prophetCalled = this.prophetCalled,
    born = this.born,
    apostleCalled = this.apostleCalled,
    notableQuotes = this.notableQuotes,
    name = this.name,
    died = this.died,
    imageUrl = this.imageUrl
)
