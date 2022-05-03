package com.example.funtranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class yoda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yoda_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}