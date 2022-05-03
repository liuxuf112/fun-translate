package com.example.funtranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class minion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.minion_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}