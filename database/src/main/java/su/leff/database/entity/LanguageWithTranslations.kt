package su.leff.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LanguageWithTranslations(
    @Embedded val language: Language,
    @Relation(
        parentColumn = "languageId",
        entityColumn = "languageId"
    )
    val translations: List<Translation>
)