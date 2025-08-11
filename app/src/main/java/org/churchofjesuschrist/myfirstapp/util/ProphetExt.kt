package org.churchofjesuschrist.myfirstapp.util

import org.churchofjesuschrist.myfirstapp.data.local.entity.ProphetEntity
import org.churchofjesuschrist.myfirstapp.webservice.dto.ProphetDto

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

