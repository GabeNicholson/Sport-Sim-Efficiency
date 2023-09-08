/**
 * A Team class representing a sports team.
 *
 * @constructor create a new Team with a name and a skill level.
 * @param name the name of the team.
 * @param skillLevel the skill level of the team, must be positive.
 * @throws IllegalArgumentException if the skill level is not positive.
 */
class Team(val name: String, val skillLevel: Int) {
  require(skillLevel > 0, "Skill level must be positive.")
}
