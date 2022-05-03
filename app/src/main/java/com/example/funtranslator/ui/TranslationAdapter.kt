package com.example.funtranslator.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.funtranslator.R
import com.example.funtranslator.data.TranslationObject

val tag = "TranslationAdapter"

class TranslationAdapter : RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder>() {
    private val translations: MutableList<TranslationObject> = mutableListOf()

    override fun getItemCount(): Int {
        return translations.size
    }

    fun addTranslation(translation: TranslationObject) {

        translations.add(0, translation)
        notifyDataSetChanged()
    }
    fun clearTranslations(){
        translations.clear();
    }

    class TranslationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val translationType: TextView = view.findViewById(R.id.tv_translation_type)
        private val translatedText: TextView = view.findViewById(R.id.tv_translated_text)
        private val btnViewTranslation = view.findViewById(R.id.btn_view_translation) as Button
        private var currentTranslation: TranslationObject? = null
        fun bind(translation: TranslationObject) {

            currentTranslation = translation
            translationType.text = translation.translationType.replaceFirstChar({ it.uppercase() })
            translatedText.text = translation.translatedText

        }
        init{

            btnViewTranslation.setOnClickListener {


                val intent = Intent(it.context, TranslationActivity::class.java).apply {
                    putExtra(EXTRA_TRANSLATION_OBJECT, currentTranslation)
                }
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.translation_list_item, parent, false)

        return TranslationViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationViewHolder, position: Int) {

        holder.bind(translations[position])
    }
}