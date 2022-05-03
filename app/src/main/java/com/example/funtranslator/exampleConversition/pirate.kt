package com.example.funtranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class pirate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pirate_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}