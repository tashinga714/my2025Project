package jetbrains.kotlin.course.alias.util

// Type alias for identifiers
typealias Identifier = Int

// Factory for generating unique identifiers
// Factory for generating unique identifiers
class IdentifierFactory(private var counter: Int = 0) {

    // Function to generate unique identifiers
    fun uniqueIdentifier(): Identifier {
        return ++counter
    }

    // Getter for current identifier value
    fun getCurrentId(): Int = counter
}
