package com.example.funtranslator.exampleConversition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.funtranslator.*

class conversation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pirate_btn = findViewById<Button>(R.id.pirate_btn)
        pirate_btn.setOnClickListener {
            val intent = Intent(this, pirate::class.java)
            startActivity(intent)
        }

        val groot_btn = findViewById<Button>(R.id.groot_btn)
        groot_btn.setOnClickListener {
            val intent = Intent(this, groot::class.java)
            startActivity(intent)
        }

        val gungan_btn = findViewById<Button>(R.id.gungan_btn)
        gungan_btn.setOnClickListener {
            val intent = Intent(this, gungan::class.java)
            startActivity(intent)
        }

        val minion_btn = findViewById<Button>(R.id.minion_btn)
        minion_btn.setOnClickListener {
            val intent = Intent(this, minion::class.java)
            startActivity(intent)
        }

        val shakespeare_btn = findViewById<Button>(R.id.shakespeare_btn)
        shakespeare_btn.setOnClickListener {
            val intent = Intent(this, shakespeare::class.java)
            startActivity(intent)
        }

        val yoda_btn = findViewById<Button>(R.id.yoda_btn)
        yoda_btn.setOnClickListener {
            val intent = Intent(this, yoda::class.java)
            startActivity(intent)
        }


    }
}