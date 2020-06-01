package su.leff.database.dao

import androidx.room.*
import su.leff.database.entity.Language
import su.leff.database.entity.LanguageWithTranslations

@Dao
interface LanguageDao {

    @Query("SELECT * FROM language")
    fun getAll(): List<Language>

    @Insert
    fun insertAll(vararg language: Language)

    @Delete
    fun delete(language: Language)

    @Transaction
    @Query("SELECT * FROM language")
    fun getLanguageWithTranslations(): List<LanguageWithTranslations>
}