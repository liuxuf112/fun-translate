package com.example.funtranslator.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funtranslator.R
import com.example.funtranslator.exampleConversition.appinfo
import com.example.funtranslator.exampleConversition.conversation
import com.example.funtranslator.data.TranslationObject
import java.util.*

class MainActivity : AppCompatActivity(){
    private val tag: String = "MainActivity"

    private lateinit var dropDown: AutoCompleteTextView
    private lateinit var editText:com.google.android.material.textfield.TextInputEditText
    private lateinit var goButton: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var viewAllButton: Button
    private val viewModel: TranslationAPIViewModel by viewModels()
    private var selectedTranslation:String = "pirate"
    private val databaseViewModel: TranslationsDatabaseViewModel by viewModels()
    private lateinit var translationListRV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
    
        goButton = findViewById(R.id.floating_action_button_go)
        goButton.setOnClickListener {

            viewModel.loadTranslationResult(editText.text.toString(),selectedTranslation)
        }

        viewAllButton = findViewById(R.id.btn_view_all)
        viewAllButton.setOnClickListener{
            val intent = Intent(this, TranslationHistoryActivity::class.java)
            startActivity(intent)
        }



        viewModel.translationResult.observe(this) { translationRequestResult ->
            if(translationRequestResult != null) {
                Log.d(tag, "translated text return from api ${translationRequestResult.contents!!.translatedText}")
                Log.d(tag, "original text return from api${translationRequestResult.contents.originalText}")
                translationRequestResult.contents.translatedAtMilliseconds = Date().toInstant().toEpochMilli()
                databaseViewModel.addTranslationObject(translationRequestResult.contents)
                startIntent(translationRequestResult.contents)
            } else {
                Log.d(tag, "Observing viewmodel Failed, likely API response is null currently")
            }
        }
        dropDown = findViewById(R.id.auto_text_view)
        //translation type selector stuff
        val items = resources.getStringArray(R.array.translations_array)

        val dropdownAdapter = ArrayAdapter(this, R.layout.list_item, items)
        dropDown.setAdapter(dropdownAdapter)
        dropDown.addTextChangedListener(textWatcher)
        //Previous Translation Stuff
        translationListRV = findViewById(R.id.rv_translation_list)
        translationListRV.layoutManager = LinearLayoutManager(this)
        translationListRV.setHasFixedSize(true)
        val adapter = TranslationAdapter()
        translationListRV.adapter = adapter


        databaseViewModel.mostRecentTwoTranslations.observe(this) { translationObjects ->
            adapter.clearTranslations()
            for (translation in translationObjects) {

                adapter.addTranslation(translation)
            }
        }
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            selectedTranslation = s.toString().lowercase()

        }
    }



    private fun startIntent(translationObject: TranslationObject)
    {
        val intent = Intent(this, TranslationActivity::class.java).apply {
            putExtra(EXTRA_TRANSLATION_OBJECT, translationObject)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.example_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.appInfo -> {
                val intent = Intent(this, appinfo::class.java)
                startActivity(intent)
                true
            }

            R.id.conversation -> {
                val intent = Intent(this, conversation::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}