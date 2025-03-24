package jetbrains.kotlin.course.alias.persistence

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jetbrains.kotlin.course.alias.results.GameResult
import jetbrains.kotlin.course.alias.results.GameResultsService
import jetbrains.kotlin.course.alias.team.Team
import jetbrains.kotlin.course.alias.team.TeamService
import java.io.File

class GameStatePersistence {
    private val mapper = jacksonObjectMapper()
    private val gameStateFile = File("gameState.json")
    private val cardStateFile = File("cardState.json")

    // Data classes for serialization
    data class GameState(
        val gameHistory: List<GameResult>,
        val teamsStorage: Map<String, Team>,
        val teamServiceCounter: Int,
        val cardServiceCounter: Int
    )

    data class CardState(
        val usedWords: List<String>
    )

    // Save game state to files
    fun saveGameState(
        teamServiceIdentifierCounter: Int,
        cardServiceIdentifierCounter: Int,
        usedWords: List<String>
    ) {
        val gameState = GameState(
            gameHistory = GameResultsService.gameHistory,
            teamsStorage = TeamService.teamsStorage.mapKeys { it.key.toString() },
            teamServiceCounter = teamServiceIdentifierCounter,
            cardServiceCounter = cardServiceIdentifierCounter
        )

        val cardState = CardState(
            usedWords = usedWords
        )

        gameStateFile.writeText(mapper.writeValueAsString(gameState))
        cardStateFile.writeText(mapper.writeValueAsString(cardState))
    }

    // Load game state from files
    fun loadGameState(): Triple<Int, Int, List<String>>? {
        if (!gameStateFile.exists() || !cardStateFile.exists()) {
            return null
        }

        try {
            val gameState = mapper.readValue<GameState>(gameStateFile.readText())
            val cardState = mapper.readValue<CardState>(cardStateFile.readText())

            // Restore game history
            GameResultsService.gameHistory.clear()
            GameResultsService.gameHistory.addAll(gameState.gameHistory)

            // Restore teams storage
            TeamService.teamsStorage.clear()
            gameState.teamsStorage.forEach { (key, team) ->
                TeamService.teamsStorage[key.toInt()] = team
            }

            return Triple(
                gameState.teamServiceCounter,
                gameState.cardServiceCounter,
                cardState.usedWords
            )
        } catch (e: Exception) {
            return null
        }
    }
}