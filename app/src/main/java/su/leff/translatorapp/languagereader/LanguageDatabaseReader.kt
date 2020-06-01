package su.leff.translatorapp.languagereader

import android.content.Context
import su.leff.database.AppDatabase
import su.leff.database.entity.LanguageWithTranslations

/**
 * Reads data from database
 */
class LanguageDatabaseReader(private val context: Context) :
    LanguageReader {

    private var languageId = 0

    override fun readNextLanguage(): HashMap<String, String> {

        val db = AppDatabase.getDatabase(context)
        val languages = db.languageDao().getLanguageWithTranslations() as ArrayList

        var map: HashMap<String, String> = HashMap()

        if (languages.isNotEmpty()) {
            if (languages.size > languageId) {
                map = loadHashmap(languages[languageId])
                languageId++
            } else {
                map = loadHashmap(languages[0])
                languageId = 1
            }
        }
        return map
    }

    /**
     * Adapts data from entity with embedded databse entities into hashmap
     */
    private fun loadHashmap(languageWithTranslations: LanguageWithTranslations): HashMap<String, String> {
        val hashmap = HashMap<String, String>()
        for (translation in languageWithTranslations.translations) {
            hashmap[translation.key] = translation.text
        }
        return hashmap
    }
}