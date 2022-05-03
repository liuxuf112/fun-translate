package com.example.funtranslator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class gungan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gungan_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}