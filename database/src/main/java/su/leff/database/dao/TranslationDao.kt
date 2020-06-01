package su.leff.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import su.leff.database.entity.Language
import su.leff.database.entity.Translation

@Dao
interface TranslationDao {

    @Query("SELECT * FROM translation")
    fun getAll(): List<Translation>

    @Insert
    fun insertAll(vararg translation: Translation)

    @Delete
    fun delete(translation: Translation)
}