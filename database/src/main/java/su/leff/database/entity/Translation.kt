package su.leff.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Translation(
    @PrimaryKey val translationId: Int,
    @ColumnInfo(name = "languageId") val languageId: Int,
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "text") val text: String
)