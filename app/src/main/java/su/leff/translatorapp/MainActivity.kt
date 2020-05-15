package su.leff.translatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import su.leff.translator.Translator
import su.leff.translator.Translator.key

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        /*
        Loadt, we will use kotlin synthetics below.
        You may use whatever you want, even a good old findViewById, just don't have nulls.
         */
        setContentView(R.layout.activity_main)

        /*
        Bind TextViews and EditText keys to translator via extension. Just declare a key.
         */
        txvHello.key = "helloworld"
        edtTest.key = "hint"

        /*
        This is how you initially load the strings. You can use it in the Application class.

        Do not forget to check out setFailSafe()
         */
        Translator.setFailSafe(false).loadMap(LanguageReader.readNextLanguage())

        btnClick.setOnClickListener {
            /*
            This is how you change the strings. TextView texts, EditText hints get updated,
            Toast shows a right string.
             */
            Translator.loadMap(LanguageReader.readNextLanguage())
            Toast.makeText(this, Translator.getString("toast"), Toast.LENGTH_SHORT).show()
        }
    }
}