package org.churchofjesuschrist.prophets.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.churchofjesuschrist.prophets.data.local.MainDatabase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        val dbFile = application.getDatabasePath(MainDatabase.DATABASE_NAME)
        val builder = Room.databaseBuilder<MainDatabase>(context = application, name = dbFile.absolutePath)
        return MainDatabase.getDatabase(builder)
    }
}