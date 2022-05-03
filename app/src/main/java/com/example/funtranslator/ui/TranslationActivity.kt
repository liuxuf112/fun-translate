package com.example.funtranslator.ui

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.capitalize
import com.example.funtranslator.R
import com.example.funtranslator.data.TranslationObject
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

const val TAG = "TranslationActivity"
const val EXTRA_TRANSLATION_OBJECT = "com.example.funtranslator.TranslationObject"

// TTS Code referenced from https://www.tutorialkart.com/kotlin-android/android-text-to-speech-kotlin-example/

class TranslationActivity : AppCompatActivity(), TextToSpeech.OnInitListener,
    AdapterView.OnItemSelectedListener {
    private var tts: TextToSpeech? = null
    private var translationObject: TranslationObject? = null
    private val viewModel: TranslationAPIViewModel by viewModels()
    private val databaseViewModel: TranslationsDatabaseViewModel by viewModels()
    private var initialDisplay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        if (intent != null && intent.hasExtra(EXTRA_TRANSLATION_OBJECT)) {
            translationObject =
                intent.getSerializableExtra(EXTRA_TRANSLATION_OBJECT) as TranslationObject
        }

        val originalShareButton: FloatingActionButton = findViewById(R.id.original_share_button)
        val originalTTSButton: FloatingActionButton = findViewById(R.id.original_tts_button)
        val translationShareButton: FloatingActionButton =
            findViewById(R.id.translation_share_button)
        val translationTTSButton: FloatingActionButton = findViewById(R.id.translation_tts_button)

        val items = resources.getStringArray(R.array.translations_array)
        val dropdownAdapter = ArrayAdapter(this, R.layout.list_item, items)
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner: Spinner = findViewById(R.id.spinner_translator_name)
        spinner.adapter = dropdownAdapter

        val translatorName = translationObject!!.translationType.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        for ((index, value) in items.withIndex()) {
            if (translatorName.equals(value, true)){
                spinner.setSelection(index)
                break
            }
        }

        spinner.onItemSelectedListener = this

        val originalTextTV: TextView = findViewById(R.id.original_text)
        originalTextTV.text = translationObject!!.originalText

        val translationTextTV: TextView = findViewById(R.id.translation_text)
        translationTextTV.text = translationObject!!.translatedText

        tts = TextToSpeech(this, this)

        originalShareButton.setOnClickListener {
            Log.d(TAG, "Original Share Button clicked")
            shareOriginalText()
        }

        originalTTSButton.setOnClickListener {
            Log.d(TAG, "Original TTS Button clicked")
            sayText(translationObject!!.originalText)
        }

        translationShareButton.setOnClickListener {
            Log.d(TAG, "Translation Share Button clicked")
            shareTranslatedText()
        }

        translationTTSButton.setOnClickListener {
            Log.d(TAG, "Translation TTS Button clicked")
            sayText(translationObject!!.translatedText)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if(initialDisplay){
            initialDisplay = false
            return
        }

        val item = parent.getItemAtPosition(pos)
        viewModel.loadTranslationResult(translationObject!!.originalText,item.toString())
        viewModel.translationResult.observe(this) { translationRequestResult ->
            if(translationRequestResult != null) {
                Log.d(tag, translationRequestResult.contents!!.translatedText)
                translationRequestResult.contents.translatedAtMilliseconds = Date().toInstant().toEpochMilli()
                databaseViewModel.addTranslationObject(translationRequestResult.contents)
                reStartIntent(translationRequestResult.contents)
            } else {
                Log.d(tag, "Observing viewmodel Failed, likely API response is null currently")
            }
        }
    }

    private fun reStartIntent(translationObject: TranslationObject)
    {
        finish();
        val intent = Intent(this, TranslationActivity::class.java).apply {
            putExtra(EXTRA_TRANSLATION_OBJECT, translationObject)
        }
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun sayText(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun shareOriginalText() {
        if (translationObject != null) {
            val text = getString(R.string.original_share_text, translationObject!!.originalText)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }
    }

    private fun shareTranslatedText() {
        if (translationObject != null) {
            val text =
                getString(R.string.translation_share_text, translationObject!!.translatedText)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}