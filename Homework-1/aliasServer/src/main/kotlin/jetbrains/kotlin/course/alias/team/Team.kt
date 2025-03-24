package jetbrains.kotlin.course.alias.team

import jetbrains.kotlin.course.alias.util.Identifier

// Data class to store team information
data class Team(
    val id: Identifier,
    var points: Int = 0
) {
    // Auto-generated team name based on ID
    val name: String = "Team#${id + 1}"
}