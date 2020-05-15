package su.leff.translator.exception

import java.lang.RuntimeException

/**
 * Exception thrown when text is not found in the text hashmap.
 */
class TextNotFoundException(message: String) : RuntimeException(message)

fun getTextNotFoundException(key: String): TextNotFoundException {
    return TextNotFoundException("Text with key $key not found. Did you forget to fill the map?")
}

