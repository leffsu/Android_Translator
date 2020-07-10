package su.leff.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import su.leff.database.dao.LanguageDao
import su.leff.database.dao.TranslationDao
import su.leff.database.entity.Language
import su.leff.database.entity.Translation

@Database(entities = arrayOf(Translation::class, Language::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
    abstract fun languageDao(): LanguageDao


    companion object {
        fun getDatabase(context: Context): AppDatabase {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-name"
            )
                .allowMainThreadQueries()
                .build()

            if (db.languageDao().getAll().isEmpty()) {
                db.languageDao().insertAll(Language(0, "russian"))
                db.languageDao().insertAll(Language(1, "english"))
                db.languageDao().insertAll(Language(2, "swedish"))

                db.translationDao().insertAll(Translation(0, 0, "helloworld", "Привет, мир"))
                db.translationDao().insertAll(Translation(1, 0, "hint", "Подсказка"))
                db.translationDao().insertAll(Translation(2, 0, "toast", "Тост"))
                db.translationDao().insertAll(Translation(3, 0, "second_activity", "Второе активити"))

                db.translationDao().insertAll(Translation(4, 1, "helloworld", "Hello World"))
                db.translationDao().insertAll(Translation(5, 1, "hint", "Hint"))
                db.translationDao().insertAll(Translation(6, 1, "toast", "Toast"))
                db.translationDao().insertAll(Translation(7, 1, "second_activity", "Second Activity"))

                db.translationDao().insertAll(Translation(8, 2, "helloworld", "Hey värld"))
                db.translationDao().insertAll(Translation(9, 2, "hint", "ledtråd"))
                db.translationDao().insertAll(Translation(10, 2, "toast", "rostat bröd"))
                db.translationDao().insertAll(Translation(11, 2, "second_activity", "Andra skärmen"))
            }
            return db
        }
    }
}