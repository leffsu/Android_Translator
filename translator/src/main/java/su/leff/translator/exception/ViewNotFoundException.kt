package su.leff.translator.exception

import java.lang.RuntimeException

/**
 * Exception thrown when view is not found in the view hashmap.
 */
class ViewNotFoundException(message: String) : RuntimeException(message)

fun getViewNotFoundException(key: Int): ViewNotFoundException {
    return ViewNotFoundException("View $key was not binded to Translator but translation was called. Did you forget to bind the TextView?")
}