package su.leff.translator

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import su.leff.translator.exception.TextNotFoundException
import su.leff.translator.exception.getTextNotFoundException
import su.leff.translator.exception.getViewNotFoundException

/**
 * @author Lev Nazarov (github.com/leffsu)
 *
 * Translator object is done for seamless translation of TextViews when language changes
 *
 * unused is suppressed because I don't like warnings
 * KDocUnresolvedReference is suppressed because it can't see the CalledFromWrongThreadException
 * but it exists.
 */
@Suppress("unused", "KDocUnresolvedReference")
object Translator {

    /**
     * This hashmap is done for saving the translation themselves
     * String Key - String
     *
     * Example:
     * "hi" - "Hello World"
     * "hi" - "Привет, Мир" (in another map)
     */
    private val textMap = HashMap<String, String>()

    /**
     * This hashmap is done for caching so that if you have EditTexts
     * that don't need to be refilled if they're changed
     */
    private val textMapPreviousConfiguration = HashMap<String, String>()

    /**
     * This hashmap is done for saving a TextView - String Key relation
     * Therefore, hashmap key is a TextView and value is a text key
     */
    private val textViewMap = HashMap<TextView, String>()

    /**
     * This hashmap is done for saving a EditText - String Key relation
     * Therefore, hashmap key is an EditText and value is a text key
     */
    private val editTextViewMap = HashMap<EditText, String>()

    /**
     * This hashmap is done for saving a EditText - String Key relation
     * Therefore, hashmap key is an EditText and value is a text key
     * Works only if editText was not changed
     */
    private val editTextViewMapIfNotChanged = HashMap<EditText, String>()

    /**
     * This hashmap is done for saving a EditText - String Key relation
     * Therefore, hashmap key is an EditText and value is a hint key
     */
    private val editTextViewHintMap = HashMap<EditText, String>()

    /**
     * This function loads a new map and updates all TextViews with their new texts
     * @param stringMap - hashmap of new translations
     * @param context - context (Activity) with which you can run on ui thread if you
     * want to execute loading on background thread
     * @exception TextNotFoundException is thrown when the text is not found in the hashmap
     */
    fun loadMap(stringMap: HashMap<String, String>, context: Context) {
        recordNewMap(stringMap)

        (context as Activity).runOnUiThread {
            updateViews()
        }
    }

    /**
     * This function loads a new map and updates all TextViews with their new texts
     * @param stringMap - hashmap of new translations
     * @exception TextNotFoundException is thrown when the text is not found in the hashmap
     * @exception CalledFromWrongThreadException is thrown when you try to update UI from another
     * thread. Use loadMap(stringMap: HashMap<String, String>, context: Context) instead.
     */
    fun loadMap(stringMap: HashMap<String, String>) {
        recordNewMap(stringMap)
        updateViews()
    }

    /**
     * This function clears old text map and inserts new values.
     * @param stringMap - hashmap of new values.
     */
    private fun recordNewMap(stringMap: HashMap<String, String>) {
        textMapPreviousConfiguration.clear()

        // Update old hashmap with new values.
        for ((key, value) in textMap) {
            textMapPreviousConfiguration[key] = value
        }

        // Clear old hashmap because it's immutable.
        textMap.clear()

        // Update old hashmap with new values.
        for ((key, value) in stringMap) {
            textMap[key] = value
        }
    }

    /**
     * This method updates all views when a new text map is supplied.
     */
    private fun updateViews() {
        // Update all TextViews.
        for ((view, key) in textViewMap) {
            view.text = getString(key)
        }

        // Update all EditTexts' texts.
        for ((view, key) in editTextViewMap) {
            view.setText(getString(key))
        }

        // Update all EditTexts' texts if they weren't changed.
        for ((view, key) in editTextViewMapIfNotChanged) {
            if (textMapPreviousConfiguration.containsKey(key)) {
                if (view.text.toString() == textMapPreviousConfiguration[key]) {
                    view.setText(getString(key))
                }
            } else {
                view.setText(getString(key))
            }
        }

        // Update all EditTexts' hints.
        for ((view, key) in editTextViewHintMap) {
            view.hint = getString(key)
        }
    }

    /**
     * Function lets the developer get the translation having the key
     * @param key - the key of the string
     * @exception TextNotFoundException when text is not found
     */
    fun getString(key: String): String {
        // Look for the string
        val string = textMap[key]
        // If it's there, return
        string?.let {
            return it
        } ?: run {
            // If it's not there, well, your bad, have your crash
            throw getTextNotFoundException(key)
        }
    }

    /**
     * The extension for binding TextView to translator
     *
     * You get the key, you set the key, TextView updates itself when new map is received
     */
    var TextView.key: String?
        get() =
            if (textViewMap.containsKey(this)) {
                textViewMap[this]
            } else {
                throw getViewNotFoundException(id)
            }
        set(value) {
            value?.let { string ->
                textViewMap[this] = string
            }
        }

    /**
     * The extension for binding EditText hints to translator
     *
     * You get the key, you set the key, TextView updates itself when new map is received
     */
    var EditText.key: String?
        get() =
            if (editTextViewMap.containsKey(this)) {
                editTextViewMap[this]
            } else {
                throw getViewNotFoundException(id)
            }
        set(value) {
            value?.let { string ->
                editTextViewMap[this] = string
            }
        }

    /**
     * The extension for binding EditText hints to translator
     *
     * You get the key, you set the key, TextView updates itself when new map is received
     */
    var EditText.keyIfNotChanged: String?
        get() =
            if (editTextViewMapIfNotChanged.containsKey(this)) {
                editTextViewMapIfNotChanged[this]
            } else {
                throw getViewNotFoundException(id)
            }
        set(value) {
            value?.let { string ->
                editTextViewMapIfNotChanged[this] = string
            }
        }

    /**
     * The extension for binding EditText hints to translator
     *
     * You get the key, you set the key, TextView updates itself when new map is received
     */
    var EditText.hintKey: String?
        get() =
            if (editTextViewHintMap.containsKey(this)) {
                editTextViewHintMap[this]
            } else {
                throw getViewNotFoundException(id)
            }
        set(value) {
            value?.let { string ->
                editTextViewHintMap[this] = string
            }
        }

    /**
     * This variable is responsible for fail-safe work of the object, which means that if this
     * variable is set to true, it will not crash when the object couldn't find text or view.
     */
    private var safeToFail = true

    fun setFailSafe(safe: Boolean): Translator {
        safeToFail = safe
        return this
    }
}