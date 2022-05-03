package com.example.funtranslator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationObjectDao {
    @Insert
    suspend fun insert(translationObject:TranslationObject)

    @Delete
    suspend fun delete(translationObject: TranslationObject)

    @Query("SELECT * FROM TranslationObject ORDER BY translatedAtMilliseconds")
    fun getAllTranslations(): Flow<List<TranslationObject>>

    @Query("SELECT * FROM TranslationObject ORDER BY translatedAtMilliseconds DESC LIMIT 2")
    fun getTwoMostRecentTranslations() : Flow<List<TranslationObject>>

}