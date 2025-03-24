package jetbrains.kotlin.course.alias

import jetbrains.kotlin.course.alias.card.CardService
import jetbrains.kotlin.course.alias.persistence.GameStatePersistence
import jetbrains.kotlin.course.alias.team.TeamService
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// Extension property to access the current identifier value
private val IdentifierFactory.currentId: Int
    get() = this.getCurrentId()

@SpringBootApplication
class AliasApplication

fun main(args: Array<String>) {
    // Initialize persistence
    val persistence = GameStatePersistence()

    // Load game state if possible
    val loadedState = persistence.loadGameState()

    // Initialize identifier factories with loaded state if available
    val teamIdentifierFactory = loadedState?.let { IdentifierFactory(it.first) } ?: IdentifierFactory()
    val cardIdentifierFactory = loadedState?.let { IdentifierFactory(it.second) } ?: IdentifierFactory()

    // Initialize services
    val teamService = TeamService(teamIdentifierFactory)
    val cardService = CardService(cardIdentifierFactory)

    // Register shutdown hook to save state when application exits
    Runtime.getRuntime().addShutdownHook(Thread {
        persistence.saveGameState(
            teamServiceIdentifierCounter = teamIdentifierFactory.currentId,
            cardServiceIdentifierCounter = cardIdentifierFactory.currentId,
            usedWords = cardService.usedWords
        )
    })

    // Run the Spring Boot application
    runApplication<AliasApplication>(*args)
}
