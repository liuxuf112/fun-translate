package com.example.funtranslator.data

import java.io.Serializable


data class TranslationRequestResult (
    val success:TotalHolder?,
    val error: ErrorHolder?,
    val contents: TranslationObject?) : Serializable
data class TotalHolder(
    val total: Int
)

data class ErrorHolder(
    val code:Int,
    val message:String
)