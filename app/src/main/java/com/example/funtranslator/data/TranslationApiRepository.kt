package com.example.funtranslator.data

import android.util.Log
import com.example.funtranslator.api.TranslationService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TranslationApiRepository(private val service:TranslationService,
                               private val ioDispatcher: CoroutineDispatcher=Dispatchers.IO)
{
    val tag = "TranslationApiRepository"
    suspend fun getTranslationResult(translationType:String,translationText:String)
    : Result<TranslationRequestResult> = withContext(ioDispatcher){
        try{
//            //when you're ready to call the actual API replace this with the API call, i.e.


//
//          val result: TranslationRequestResult = (TranslationRequestResult(
//            TotalHolder(1)
//            ,ErrorHolder(0,"No Error"),
//            TranslationObject(originalText="Master Obiwan has lost a planet."
//                , translatedAtMilliseconds = Date().toInstant().toEpochMilli(),
//                translatedText = "Lost a planet,  master obiwan has.",
//                translationType = "yoda"
//            )))
            Log.d(tag,"Translation Type: $translationType, and text: $translationText");
            val result = service.getTranslation(translationType,translationText) //problem is within here
            Log.d(tag,"Result: ${result.contents!!.translatedText}")
            if(result.error != null){
                Log.d(tag,"Got an error in result: ${result.error.message}")
            }



            Result.success(result)
        }catch(e:Exception){
            Log.d(tag,e.toString())
            Result.failure(e)
        }
    }
}