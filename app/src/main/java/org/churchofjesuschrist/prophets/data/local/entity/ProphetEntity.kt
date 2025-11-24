package org.churchofjesuschrist.prophets.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.Period
import java.time.LocalDate as JavaLocalDate

@Entity(tableName = "Prophets")
data class ProphetEntity(
    val prophetCalled: String? = null,
    val born: String? = null,
    val apostleCalled: String? = null,
    val notableQuotes: List<String> = emptyList(),
    @PrimaryKey val name: String,
    val died: String? = null,
    val imageUrl: String,
) {
    fun getAgeAtDeathOrCurrent(): Int {
        if (born.isNullOrBlank()) return 0
        val birthDate = LocalDate.parse(born).toJavaLocalDate()
        val endDate = if (died.isNullOrBlank()) {
            JavaLocalDate.now()
        } else {
            LocalDate.parse(died).toJavaLocalDate()
        }
        return Period.between(birthDate, endDate).years
    }
}
