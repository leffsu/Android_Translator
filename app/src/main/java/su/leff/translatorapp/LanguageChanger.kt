package su.leff.translatorapp

import android.content.Context
import su.leff.translator.Translator
import su.leff.translatorapp.languagereader.LanguageDatabaseReader
import su.leff.translatorapp.languagereader.LanguageExampleReader
import su.leff.translatorapp.languagereader.LanguageIniFileReader
import su.leff.translatorapp.languagereader.LanguageReader

object LanguageChanger {

    /*
    0 - example reader
    1 - ini file reader
    2 - database reader
     */
    var whatReaderToInit = 0

    lateinit var languageReader: LanguageReader

    fun changeLanguage(context: Context) {


        /*
        Let's create a language reader. I provide you with few examples:
        1. LanguageExampleReader - it's just hardcoded in init.
        2. LanguageIniFileReader - it reads properties from .ini file.
        3. LanguageDatabaseReader - it reads data from database.
         */
        when (whatReaderToInit) {
            0 -> {
                languageReader =
                    LanguageExampleReader()
            }
            1 -> {
                languageReader =
                    LanguageIniFileReader(context)
            }
            2 -> {
                languageReader =
                    LanguageDatabaseReader(context)
            }
        }

        /*
        This is how you initially load the strings. You can use it in the Application class.

        Do not forget to check out setFailSafe()
         */
        Translator.setFailSafe(false)
            .loadMap(languageReader.readNextLanguage())
    }

    fun nextLanguage(){
        /*
        This is how you change the strings. TextView texts, EditText hints get updated,
        Toast shows a right string.
         */
        Thread().run {
            Translator
                .loadMap(languageReader.readNextLanguage())
        }
    }
}