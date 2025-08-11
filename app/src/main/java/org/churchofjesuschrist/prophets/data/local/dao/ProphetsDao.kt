package org.churchofjesuschrist.prophets.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity

@Dao
interface ProphetsDao {
    @Query("SELECT * FROM Prophets")
    fun getAllProphetsFlow(): Flow<List<ProphetEntity>>

    @Query("SELECT * FROM Prophets WHERE name = :name")
    fun getProphetByNameFlow(name: String): Flow<ProphetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(prophet: ProphetEntity)

    @Delete
    suspend fun deleteProphet(prophet: ProphetEntity)
}
