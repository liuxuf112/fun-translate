package com.example.funtranslator.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable
import java.util.*

@Entity
data class TranslationObject(
    @PrimaryKey(autoGenerate = true) val translationObjectID:Int = 0,   //a number that just increments
    @Json(name="text") val originalText: String,
    @Json(name="translated") val translatedText:String,
    @Json(name="translation") var translationType:String,
   var translatedAtMilliseconds: Long?
) : Serializable

