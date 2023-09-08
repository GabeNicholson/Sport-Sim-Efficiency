import scala.reflect.internal.util.NoPosition.end
import scala.util.Random

/**
 * Represents a sports season consisting of multiple teams.
 * The winner of matches are decided by random number simulations with
 * probabilities defined by the two team's skill levels.
 *
 * @constructor Create a new tournament with a list of teams.
 * @param teams: List of teams participating in the tournament.
 */
class Tournament(teams: List[Team]) {
  /**
   *  Calculate the probability that `team1` will win against `team2`.
   *
   * @param team1
   * @param team2
   * @return Probability that `team1` will win.
   */
  private def calculateWinProbability(team1: Team, team2: Team): Double = {
    val totalSkill = team1.skillLevel + team2.skillLevel
    val probTeam1Wins = team1.skillLevel.toDouble / totalSkill
    return probTeam1Wins
  }

  /**
   * Simulate a match between two teams and return the winner.
   *
   * @param team1 The first team.
   * @param team2 The second team.
   * @return The team that wins the match.
   */
  def simulateMatch(team1: Team, team2: Team): Team = {
    val probTeam1Wins = calculateWinProbability(team1, team2)
    val winningTeam = if (Random.nextDouble() < probTeam1Wins) team1 else team2
    return winningTeam
  }

  /**
   * Simulate the regular season and return the list of winning teams.
   *
   * @return List of winning teams from each match.
   */
  def simulateRegularSeason(): List[Team] = {
    var results: List[Team] = List()
    for (team1 <- teams) {
      for (team2 <- teams) {
        if (team1 != team2) {
          val winningTeam = simulateMatch(team1, team2)
          results = winningTeam :: results
        }
      }
    }
    return results
  }

  /**
   * Get the top N teams based on their scores in the regular season.
   *
   * @param seasonScores List of teams that won in the regular season.
   * @param N            Number of top teams to return.
   * @return List of top N teams.
   */
  def getTopNTeams(seasonScores: List[Team], N: Int): List[Team] = {
//    Gets the top N teams based on the number of wins which is found by (occurrences.size)
    val topNTeams = seasonScores
      .groupBy(identity)
      .map { case (name, occurrences) => (name, occurrences.size) }
      .toList
      .sortWith((a, b) => (a._2 > b._2))
      .take(N)
    val best = topNTeams.map(x => x._1)
    return best
  }


  def simulatePlayoffMatch(bestTeams: List[Team]): Team = {
    var teamWin1 = 0
    var teamWin2 = 0
    while (teamWin1 < 3 & teamWin2 < 3) {
      val winner = simulateMatch(bestTeams(0), bestTeams(1))
      if (winner == bestTeams(0)) {
        teamWin1 += 1
      }
      else teamWin2 += 1
    }
    if (teamWin1 > teamWin2) bestTeams(0) else bestTeams(1)
  }
}
