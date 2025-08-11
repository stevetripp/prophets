package org.churchofjesuschrist.prophets.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.churchofjesuschrist.prophets.data.local.dao.ProphetsDao
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity
import org.churchofjesuschrist.prophets.data.local.util.StringListConverter

@Database(entities = [ProphetEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun prophetsDao(): ProphetsDao

    companion object {
        const val DATABASE_NAME: String = "main"

        fun getDatabase(builder: Builder<MainDatabase>): MainDatabase {
            return builder
                .fallbackToDestructiveMigration(true)
                .build()
        }
    }
}
