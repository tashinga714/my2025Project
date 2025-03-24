package jetbrains.kotlin.course.alias.util

import alias.JsCard
import alias.JsTeam
import jetbrains.kotlin.course.alias.card.Card
import jetbrains.kotlin.course.alias.results.GameJsResult
import jetbrains.kotlin.course.alias.results.GameResult
import jetbrains.kotlin.course.alias.team.Team
import jetbrains.kotlin.course.alias.team.TeamService

// Define the Team class with a mutable 'points' property
class Team(val id: Int, var points: Int, val name: String)

// Extension function to convert Card to JsCard
fun Card.toJsCard(): JsCard = JsCard(this.id, this.words.map { it.word }.toTypedArray())

// Extension function to convert Team to JsTeam
fun Team.toJsTeam(): JsTeam = JsTeam(this.id, this.points, this.name)

// Extension function to convert a list of Teams to an array of JsTeams
fun List<Team>.toArrayJsTeams(): Array<JsTeam> = this.map { it.toJsTeam() }.toTypedArray()

// Convert GameJsResult to GameResult
fun GameJsResult.toGameResult(): GameResult = this.map {
    val team = TeamService.teamsStorage[it.id] ?: error("Internal error! Unknown team with id: ${it.id} was received!")
    team.points = it.points  // Now works because 'points' is mutable
    team
}
