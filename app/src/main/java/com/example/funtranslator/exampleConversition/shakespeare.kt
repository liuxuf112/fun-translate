package com.example.funtranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class shakespeare : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shakespeare_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}