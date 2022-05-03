package com.example.funtranslator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.funtranslator.data.TranslationDatabase
import com.example.funtranslator.data.TranslationObject
import com.example.funtranslator.data.TranslationObjectRepository
import kotlinx.coroutines.launch

class TranslationsDatabaseViewModel(application: Application): AndroidViewModel(application) {
    private val repository = TranslationObjectRepository(TranslationDatabase.getInstance(application).translationDao())
    val allTranslations = repository.getAllTranslations().asLiveData()
    val mostRecentTwoTranslations = repository.getTwoMostRecentTranslations().asLiveData()
    fun addTranslationObject(translationObject: TranslationObject){
        viewModelScope.launch{
            repository.insertTranslationObject(translationObject)
        }
    }
    fun removeTranslationObject(translationObject: TranslationObject){
        viewModelScope.launch{
            repository.deleteTranslationObject(translationObject)
        }
    }

}