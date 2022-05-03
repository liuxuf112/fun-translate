package com.example.funtranslator.data

class TranslationObjectRepository(private val dao:TranslationObjectDao) {
    suspend fun insertTranslationObject(translationObject: TranslationObject) = dao.insert(translationObject)
    suspend fun deleteTranslationObject(translationObject: TranslationObject) = dao.delete(translationObject)
    fun getAllTranslations() = dao.getAllTranslations()
    fun getTwoMostRecentTranslations() = dao.getTwoMostRecentTranslations()
}