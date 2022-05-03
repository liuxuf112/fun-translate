package com.example.funtranslator.exampleConversition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.funtranslator.R

class appinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_info_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}