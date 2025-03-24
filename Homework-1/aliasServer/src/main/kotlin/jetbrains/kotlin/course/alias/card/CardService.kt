package jetbrains.kotlin.course.alias.card

import jetbrains.kotlin.course.alias.util.Identifier
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import kotlin.random.Random
import org.springframework.stereotype.Service

@Service // Added @Service annotation to register as a Spring bean
class CardService(
    private val identifierFactory: IdentifierFactory = IdentifierFactory()
) {
    companion object {
        // Number of words per card
        const val WORDS_IN_CARD = 4

        val usedWordsList: List<String>
            get() = words

        // Number of total words to generate
        const val TOTAL_WORDS_COUNT = 100

        // Generate random words
        private val words = generateRandomWords()

        // Calculate cards amount based on words list
        val cardsAmount = words.size / WORDS_IN_CARD

        // Function to generate random words (removed the parameter to fix the warning)
        private fun generateRandomWords(): List<String> {
            val commonWords = listOf(
                "time", "person", "year", "way", "day", "thing", "man", "world",
                "life", "hand", "part", "child", "eye", "woman", "place", "work",
                "week", "case", "point", "government", "company", "number", "group",
                "problem", "fact", "money", "water", "month", "lot", "right", "study",
                "book", "job", "system", "program", "question", "home", "business",
                "side", "kind", "head", "house", "service", "friend", "father", "power"
            )

            // Return random selection from common words, or generate more if needed
            return if (TOTAL_WORDS_COUNT <= commonWords.size) {
                commonWords.shuffled().take(TOTAL_WORDS_COUNT)
            } else {
                // If we need more words than in our list, we'll repeat with random suffixes
                val result = mutableListOf<String>()
                repeat(TOTAL_WORDS_COUNT) {
                    val baseWord = commonWords[Random.nextInt(commonWords.size)]
                    if (it < commonWords.size) {
                        result.add(baseWord)
                    } else {
                        result.add("$baseWord${Random.nextInt(100)}")
                    }
                }
                result
            }
        }
    }

    // Convert String list to Word list
    private fun List<String>.toWords(): List<Word> = this.map { Word(it) }

    // Implemented the getUsedWords method that was causing the NotImplementedError
    val usedWords: List<String>
        get() {
            return usedWordsList
        }

    // List of all cards
    val cards: List<Card> = generateCards()

    // Generate cards from words
    private fun generateCards(): List<Card> {
        // Shuffle words and split into chunks
        val shuffledWords = words.shuffled()
        val wordChunks = shuffledWords.chunked(WORDS_IN_CARD).take(cardsAmount)

        // Create cards from chunks
        return wordChunks.map { chunk ->
            Card(
                id = identifierFactory.uniqueIdentifier(),
                words = chunk.toWords()
            )
        }
    }

    // Get card by index
    fun getCardByIndex(index: Int): Card {
        return cards.getOrNull(index) ?: throw IllegalArgumentException("Card with index $index does not exist")
    }
}