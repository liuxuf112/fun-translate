package com.example.funtranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class groot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groot_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}