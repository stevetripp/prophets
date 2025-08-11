package org.churchofjesuschrist.myfirstapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.churchofjesuschrist.myfirstapp.data.local.entity.ProphetEntity

@Dao
interface ProphetsDao {
    @Query("SELECT * FROM Prophets")
    fun getAllProphetsFlow(): Flow<List<ProphetEntity>>

    suspend fun getAllProphets(): List<ProphetEntity> = getAllProphetsFlow().first()

    @Query("SELECT * FROM Prophets WHERE name = :name")
    suspend fun getProphetByName(name: String): ProphetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(prophet: ProphetEntity)

    @Delete
    suspend fun deleteProphet(prophet: ProphetEntity)
}
