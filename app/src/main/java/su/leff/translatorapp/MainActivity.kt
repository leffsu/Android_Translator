package su.leff.translatorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import su.leff.translator.Translator
import su.leff.translator.Translator.hintKey
import su.leff.translator.Translator.key
import su.leff.translator.Translator.keyIfNotChanged
import su.leff.translatorapp.languagereader.LanguageDatabaseReader
import su.leff.translatorapp.languagereader.LanguageExampleReader
import su.leff.translatorapp.languagereader.LanguageIniFileReader
import su.leff.translatorapp.languagereader.LanguageReader

class MainActivity : AppCompatActivity() {

    val translator = Translator

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        /*
        We will use kotlin synthetics below.
        You may use whatever you want, even a good old findViewById, just don't have nulls.
         */
        setContentView(R.layout.activity_main)


        // Check it out!
        LanguageChanger.changeLanguage(this)

        /*
        Bind TextViews and EditText keys to translator via extension. Just declare a key.
         */
        txvHello.key = "helloworld"
        edtTest.key = "hint"
        edtTestHint.hintKey = "hint"
        edtTestChanged.keyIfNotChanged = "hint"
        btnSecondActivity.key = "second_activity"

        Toast.makeText(this, Translator.getString("toast"), Toast.LENGTH_SHORT).show()

        btnClick.setOnClickListener {

            // Check it out!
            LanguageChanger.nextLanguage()

            Toast.makeText(this, Translator.getString("toast"), Toast.LENGTH_SHORT).show()
        }

        btnSecondActivity.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}