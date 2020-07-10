package su.leff.translatorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import su.leff.translator.Translator.key

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        txvHello.key = "helloworld"
        btnChangeLanguage.setOnClickListener {
            LanguageChanger.nextLanguage()
        }
    }
}