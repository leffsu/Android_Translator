package su.leff.translatorapp

/**
 * This is just an example of how your .ini or database reader could look like.
 *
 * The only purpose of this example is to show the format in which the strings must be loaded
 */
object LanguageReader {

    private var languageId = 0

    private val russianHashMap = HashMap<String, String>()
    private val englishHashMap = HashMap<String, String>()
    private val swedishHashMap = HashMap<String, String>()

    init {
        russianHashMap["helloworld"] = "Привет, Мир"
        russianHashMap["hint"] = "Подсказка"
        russianHashMap["toast"] = "Тост"

        englishHashMap["helloworld"] = "Hello World"
        englishHashMap["hint"] = "Hint"
        englishHashMap["toast"] = "Toast"

        swedishHashMap["helloworld"] = "Hey värld"
        swedishHashMap["hint"] = "ledtråd"
        swedishHashMap["toast"] = "rostat bröd"
    }

    fun readNextLanguage(): HashMap<String, String> {
        return when (languageId){
            0 -> {
                languageId = 1
                russianHashMap
            }
            1 -> {
                languageId = 2
                englishHashMap
            }
            else -> {
                languageId = 0
                swedishHashMap
            }
        }
    }
}