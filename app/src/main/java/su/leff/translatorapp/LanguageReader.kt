package su.leff.translatorapp

import android.content.Context

interface LanguageReader {
    fun readNextLanguage(): HashMap<String, String>
}