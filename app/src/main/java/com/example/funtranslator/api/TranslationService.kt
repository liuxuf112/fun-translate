package com.example.funtranslator.api


import com.example.funtranslator.data.TotalHolder
import com.example.funtranslator.data.TranslationObject
import com.example.funtranslator.data.TranslationRequestResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.Query


interface TranslationService {

    @GET("/translate/{translationType}.json")
    suspend fun getTranslation(
        @Path("translationType",encoded=true) translationType:String,
        @Query("text",encoded=false) translationText:String
        ) : TranslationRequestResult



    companion object{
        private const val BASE_URL="https://api.funtranslations.com"
        fun create():TranslationService{
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(TranslationService::class.java)
        }

    }
}