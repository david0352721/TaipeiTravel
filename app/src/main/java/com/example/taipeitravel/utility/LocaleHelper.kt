package com.example.taipeitravel.utility

import android.content.Context
import java.util.*

class LocaleHelper {

    private val selectLanguage = "language"
    private val selectCountry = "country"
    private val selectApiLang = "apiLang"

    fun getLanguage(context: Context): String {
        return getLanguageData(context, Locale.getDefault().language).toString()
    }

    fun getCountry(context: Context): String {
        return getCountryData(context, Locale.getDefault().country).toString()
    }

    fun setLocale(context: Context, language: String, country: String): Context {
        persist(context, language, country)
        return updateResources(context, language, country)
    }

    private fun getLanguageData(context: Context, defaultLanguage: String): String? {
        val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return preferences.getString(selectLanguage, defaultLanguage)
    }

    private fun getCountryData(context: Context, defaultCountry: String): String? {
        val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return preferences.getString(selectCountry, defaultCountry)
    }

    private fun persist(context: Context, language: String, country: String) {
        val apiLang: String = if (language.contains("en")) {
            "en"
        } else if (language.contains("ja")) {
            "ja"
        } else {
            "zh-tw"
        }

        val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(selectLanguage, language)
        editor.putString(selectCountry, country)
        editor.putString(selectApiLang, apiLang)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String, country: String): Context {
        val locale = Locale(language, country)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

}