package jetbrains.kotlin.course.alias.results

// Removed potentially problematic import if not used
// import com.jetbrains.exported.JBRApi
import jetbrains.kotlin.course.alias.team.Team
import jetbrains.kotlin.course.alias.team.TeamService
import org.springframework.stereotype.Service

@Service
class GameResultsService {
    companion object {
        // Storage for game history
        val gameHistory: MutableList<GameResult> = mutableListOf()
    }

    // Save game results to history
    fun saveGameResults(result: GameResult) {
        // Check if result is not empty
        if (result.isEmpty()) {
            throw IllegalArgumentException("Game result cannot be empty")
        }

        // Check if all team IDs are in the teamsStorage
        result.forEach { team ->
            if (!TeamService.teamsStorage.containsKey(team.id)) {
                throw IllegalArgumentException("Team with id ${team.id} is not registered in the system")
            }
        }

        // Add result to game history
        gameHistory.add(result)
    }

    // Get all game results in reverse order
    fun getAllGameResults(): List<GameResult> {
        return gameHistory.reversed()
    }
}