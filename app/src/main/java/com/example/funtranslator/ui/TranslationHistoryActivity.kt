package com.example.funtranslator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funtranslator.R

class TranslationHistoryActivity : AppCompatActivity() {
    private lateinit var translationListRV: RecyclerView
    private val databaseViewModel: TranslationsDatabaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation_history)

        translationListRV = findViewById(R.id.rv_translation_history_list)
        translationListRV.layoutManager = LinearLayoutManager(this)
        translationListRV.setHasFixedSize(true)
        val adapter = TranslationAdapter()
        translationListRV.adapter = adapter


        databaseViewModel.allTranslations.observe(this) { translationObjects ->
            adapter.clearTranslations()
            for (translation in translationObjects) {
                adapter.addTranslation(translation)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}