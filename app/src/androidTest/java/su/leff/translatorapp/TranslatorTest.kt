package su.leff.translatorapp

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import su.leff.translatorapp.languagereader.LanguageDatabaseReader
import su.leff.translatorapp.languagereader.LanguageExampleReader
import su.leff.translatorapp.languagereader.LanguageIniFileReader

@RunWith(AndroidJUnit4::class)
@LargeTest
class TranslatorTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    lateinit var activity: MainActivity

    @Before
    fun setup() {
        val intent = Intent(Intent.ACTION_PICK)
        activity = rule.launchActivity(intent)
    }

    @Test
    fun languageChangesOnTheFirstScreenWhenChangedOnTheSecondScreen() {
        waitFor(SLEEP_TIME_IN_SECONDS_BEFORE_TEST)
        checkRussianText()
        clickChangeLanguageButton()
        checkEnglishText()
        clickOnSecondActivityButton()
        onView(withId(R.id.txvHello))
            .check(matches(withText(englishTextViewText)))
        onView(withId(R.id.btnChangeLanguage))
            .perform(click())
        onView(withId(R.id.txvHello))
            .check(matches(withText(swedishTextViewText)))
        pressBack()
        checkSwedishText()
    }

    /**
     * Checking Translator with example reader
     */
    @Test
    fun languageChangesWithExampleReader() {
        LanguageChanger.whatReaderToInit = 0
        assert(LanguageChanger.languageReader is LanguageExampleReader)
        runCommonTest()
    }

    /**
     * Checking Translator with file ini reader
     */
    @Test
    fun languageChangesWithFileIniReader() {
        LanguageChanger.whatReaderToInit = 1
        assert(LanguageChanger.languageReader is LanguageIniFileReader)
        runCommonTest()
    }

    /**
     * Checking Translator with database reader
     */
    @Test
    fun languageChangesWithDatabaseReader() {
        LanguageChanger.whatReaderToInit = 2
        assert(LanguageChanger.languageReader is LanguageDatabaseReader)
        runCommonTest()
    }

    /**
     * This test checks if EditText's text should not be replaced if it was somehow changed
     */
    @Test
    fun checkEditTextsThatWerentChanged() {
        waitFor(SLEEP_TIME_IN_SECONDS_BEFORE_TEST)
        // Let's write some text into the field
        onView(withId(R.id.edtTestChanged))
            .perform(typeText(SOME_TEXT))
        Espresso.closeSoftKeyboard()
        // Change the language
        clickChangeLanguageButton()
        // Text must not be changed
        onView(withId(R.id.edtTestChanged))
            .check(matches(withText(SOME_TEXT)))
    }

    /**
     * We check TextView's text, EditText's hint and EditText's text.
     *
     * Clicking the button to change the language.
     */
    private fun runCommonTest() {
        waitFor(SLEEP_TIME_IN_SECONDS_BEFORE_TEST)
        checkRussianText()
        clickChangeLanguageButton()
        checkEnglishText()
        clickChangeLanguageButton()
        checkSwedishText()
        clickChangeLanguageButton()
        checkRussianText()
    }

    private fun checkRussianText() {
        checkTextView(russianTextViewText)
        checkEditTextText(russianEditTextText)
        checkEditTextHint(russianEditTextText)
        checkText(russianTextViewText, russianEditTextText, russianToastText)
        waitFor(SLEEP_TIME_IN_SECONDS)
    }

    private fun checkEnglishText() {
        checkTextView(englishTextViewText)
        checkEditTextText(englishEditTextText)
        checkEditTextHint(englishEditTextText)
        checkText(englishTextViewText, englishEditTextText, englishToastText)
        waitFor(SLEEP_TIME_IN_SECONDS)
    }

    private fun checkSwedishText() {
        checkTextView(swedishTextViewText)
        checkEditTextText(swedishEditTextText)
        checkEditTextHint(swedishEditTextText)
        checkText(swedishTextViewText, swedishEditTextText, swedishToastText)
        waitFor(SLEEP_TIME_IN_SECONDS)
    }

    private fun waitFor(seconds: Int) {
        Thread.sleep((seconds * 1000).toLong())
    }

    private fun clickChangeLanguageButton() {
        onView(withId(R.id.btnClick))
            .perform(click())
    }

    private fun checkTextView(string: String) {
        onView(withId(R.id.txvHello))
            .check(matches(withText(string)))
    }

    private fun checkEditTextText(string: String) {
        onView(withId(R.id.edtTest))
            .check(matches(withText(string)))
    }

    private fun checkEditTextHint(string: String) {
        onView(withId(R.id.edtTestHint))
            .check(matches(withHint(string)))
    }

    private fun clickOnSecondActivityButton(){
        onView(withId(R.id.btnSecondActivity))
            .perform(click())
    }

    private fun checkText(
        textViewString: String,
        editTextString: String,
        toastString: String
    ) {
        assert(activity.translator.getString(textViewKey) == textViewString)
        assert(activity.translator.getString(editTextKey) == editTextString)
        assert(activity.translator.getString(toastKey) == toastString)
    }

    companion object {

        private const val russianTextViewText = "Привет, Мир"
        private const val russianEditTextText = "Подсказка"
        private const val russianToastText = "Тост"

        private const val englishTextViewText = "Hello World"
        private const val englishEditTextText = "Hint"
        private const val englishToastText = "Toast"

        private const val swedishTextViewText = "Hey värld"
        private const val swedishEditTextText = "Ledtråd"
        private const val swedishToastText = "Rostat bröd"

        private const val textViewKey = "helloworld"
        private const val editTextKey = "hint"
        private const val toastKey = "toast"

        private const val SLEEP_TIME_IN_SECONDS = 3
        private const val SLEEP_TIME_IN_SECONDS_BEFORE_TEST = 30

        private const val SOME_TEXT = "test"
    }
}

/*
APPCENTER_AUTH=d610b2beeeb8a6577473a24db247e63189e0b005 appcenter test run espresso --app "App-Center-Test-Cloud/1234-Andriod-test" --devices "App-Center-Test-Cloud/android-q" --app-path /Users/leff/Documents/repo/Android_Translator/app/build/outputs/apk/debug/app-debug.apk --test-series "master" --locale "en_US" --build-dir /Users/leff/Documents/repo/Android_Translator/app/build/outputs/apk/androidTest/debug
 */