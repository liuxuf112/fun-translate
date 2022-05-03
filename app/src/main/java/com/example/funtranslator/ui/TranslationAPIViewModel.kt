package com.example.funtranslator.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.funtranslator.api.TranslationService
import com.example.funtranslator.data.TranslationApiRepository
import com.example.funtranslator.data.TranslationRequestResult
import kotlinx.coroutines.launch

class TranslationAPIViewModel : ViewModel() {
    private val repository = TranslationApiRepository(TranslationService.create())
    val tag="TranslationAPIViewModel"
    private var _translationResult = MutableLiveData<TranslationRequestResult?>(null)
    val translationResult: LiveData<TranslationRequestResult?> =
        _translationResult

    fun loadTranslationResult(textToBeTranslated:String,translationType:String){
        //need to create json file here
        viewModelScope.launch{
            val result = repository.getTranslationResult(translationType,textToBeTranslated)
            Log.d(tag,"In Translation API View Model results: ${result.getOrNull().toString()}")
            _translationResult.value = result.getOrNull()
        }
    }
}