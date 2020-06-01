package su.leff.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Language(
    @PrimaryKey val languageId: Int,
    @ColumnInfo(name = "name") val name: String
)