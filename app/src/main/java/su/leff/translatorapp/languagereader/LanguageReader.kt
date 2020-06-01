package su.leff.translatorapp.languagereader

import android.content.Context

interface LanguageReader {
    fun readNextLanguage(): HashMap<String, String>
}