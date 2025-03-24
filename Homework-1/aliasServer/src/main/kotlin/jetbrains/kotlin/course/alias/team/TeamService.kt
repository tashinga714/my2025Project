package jetbrains.kotlin.course.alias.team

import jetbrains.kotlin.course.alias.util.Identifier
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service


@Service
class TeamService(
    private val identifierFactory: IdentifierFactory = IdentifierFactory()
) {
    companion object {
        // Storage for all teams
        val teamsStorage: MutableMap<Identifier, Team> = mutableMapOf()
    }

    // Generate teams for one round of the game
    fun generateTeamsForOneRound(teamsNumber: Int): List<Team> {
        val teams = List(teamsNumber) {
            val id = identifierFactory.uniqueIdentifier()
            Team(id)
        }

        // Store all teams in the teamsStorage
        teams.forEach { team ->
            teamsStorage[team.id] = team
        }

        return teams
    }
}