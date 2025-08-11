package org.churchofjesuschrist.myfirstapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prophets")
data class ProphetEntity(
    val prophetCalled: String? = null,
    val born: String? = null,
    val apostleCalled: String? = null,
    val notableQuotes: List<String> = emptyList(),
    @PrimaryKey val name: String,
    val died: String? = null,
    val imageUrl: String,
)
