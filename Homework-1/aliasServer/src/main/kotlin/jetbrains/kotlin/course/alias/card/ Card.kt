package jetbrains.kotlin.course.alias.card

import jetbrains.kotlin.course.alias.util.Identifier

// Value class to store a word
@JvmInline
value class Word(val word: String)

// Data class to store card information
data class Card(
    val id: Identifier,
    val words: List<Word>
)