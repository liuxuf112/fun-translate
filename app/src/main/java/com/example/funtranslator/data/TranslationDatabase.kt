package com.example.funtranslator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[TranslationObject::class],version=2, )
abstract class TranslationDatabase : RoomDatabase(){
    abstract fun translationDao(): TranslationObjectDao
    companion object{
        const val DATABASE_NAME="Translations Database"
        @Volatile private var instance: TranslationDatabase? = null
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                TranslationDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        fun getInstance(context: Context): TranslationDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }


}