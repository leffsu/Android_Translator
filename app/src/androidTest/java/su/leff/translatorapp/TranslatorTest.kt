package su.leff.translatorapp


import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import su.leff.translatorapp.languagereader.LanguageDatabaseReader
import su.leff.translatorapp.languagereader.LanguageExampleReader
import su.leff.translatorapp.languagereader.LanguageIniFileReader


@RunWith(AndroidJUnit4::class)
class TranslatorTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, false, true)

    lateinit var activity: MainActivity

    private val SLEEP_TIME_IN_SECONDS = 3

    @Before
    fun setup() {
        val intent = Intent(Intent.ACTION_PICK)
        activity = rule.launchActivity(intent)
    }

    /**
     * Checking Translator with example reader
     */
    @Test
    fun languageChangesWithExampleReader() {
        MainActivity.whatReaderToInit = 0
        assert(activity.languageReader is LanguageExampleReader)
        runCommonTest()
    }

    /**
     * Checking Translator with file ini reader
     */
    @Test
    fun languageChangesWithFileIniReader() {
        MainActivity.whatReaderToInit = 1
        assert(activity.languageReader is LanguageIniFileReader)
        runCommonTest()
    }

    /**
     * Checking Translator with database reader
     */
    @Test
    fun languageChangesWithDatabaseReader() {
        MainActivity.whatReaderToInit = 2
        assert(activity.languageReader is LanguageDatabaseReader)
        runCommonTest()
    }


    /**
     * We check txvHello's test, edtTest's hint and the toast's contents.
     * In order to check if toast's message is correct, we're looking for the toast with
     * specific message. If it's displayed - it's ok.
     *
     * Then we're waiting for @see SLEEP_TIME_IN_SECONDS so that toasts had time to disappear.
     * Getting an error otherwise.
     *
     * Clicking the button to change the language.
     */
    private fun runCommonTest() {
        checkHelloText("Привет, Мир")
        checkEditHint("Подсказка")
        waitFor(SLEEP_TIME_IN_SECONDS)
        clickChangeLanguageButton()

        checkHelloText("Hello World")
        checkEditHint("Hint")
        waitFor(SLEEP_TIME_IN_SECONDS)
        clickChangeLanguageButton()

        checkHelloText("Hey värld")
        checkEditHint("ledtråd")
        waitFor(SLEEP_TIME_IN_SECONDS)
        clickChangeLanguageButton()

        checkHelloText("Привет, Мир")
        checkEditHint("Подсказка")
        waitFor(SLEEP_TIME_IN_SECONDS)
    }

    private fun waitFor(seconds: Int) {
        Thread.sleep((seconds * 1000).toLong())
    }

    private fun clickChangeLanguageButton() {
        onView(withId(R.id.btnClick))
            .perform(click())
    }

    private fun checkHelloText(string: String) {
        onView(withId(R.id.txvHello))
            .check(matches(withText(string)))
    }

    private fun checkEditHint(string: String) {
        onView(withId(R.id.edtTest))
            .check(matches(withHint(string)))
    }
}