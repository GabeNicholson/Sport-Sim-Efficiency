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
   * The total number of games played in one iteration is equal to (N * N-1) where N is the number of teams.
   *
   * @param numGameIterations Decides many times the teams will play against each other.
   * @return List of winning teams from each match. Each element in the list is a Team instance and the total wins
   *         is found by counting how many times that Team instance is in the list.
   */
  def simulateRegularSeason(numGameIterations: Int): List[Team] = {
    require(numGameIterations > 0, "Number of game iterations must be greater than zero.")
    val results = for {
      sim <- 1 to numGameIterations
      team1 <- teams
      team2 <- teams
      if (team1 != team2)
    } yield simulateMatch(team1, team2)
    return results.toList
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
//    TopNTeams is a list of tuples with the structure (Team, #Wins).
    val topNTeams = seasonScores
      .groupBy(identity)
      .map { case (name, occurrences) => (name, occurrences.size) }
      .toList
      .sortWith((a, b) => a._2 > b._2)
      .take(N)
//    Extract the Team instance from the list
    val bestTeams: List[Team] = topNTeams.map(x => x._1)
    return bestTeams
  }


  /** Simulates a best of five playoff match between two teams.
   *
   * The first team to get 3 points wins. In future iterations this can become a variable.
   *
   * @param team1 Can be any Team.
   * @param team2 Can be any Team.
   * @return The Team that won the match.
   */
  def simulatePlayoffMatch(team1: Team, team2: Team): Team = {
    def inner(teamWin1: Int, teamWin2: Int): Team = {
      if (teamWin1 == 3) team1
      else if (teamWin2 == 3) team2
      else {
        val winner = simulateMatch(team1, team2)
        if (winner == team1) inner(teamWin1 + 1, teamWin2)
        else inner(teamWin1, teamWin2 + 1)
      }
    }
    inner(0,0)
  }

  /** Simulate a single round in the playoffs.
   *
   * First start by getting the first two teams in the list. Then get these teams to play against each other
   * and store the winner in `accumulator`. Run this function again until there aren't any teams left.
   *
   * @param playoffTeams
   * @return
   */
  private def simKnockoutRound(playoffTeams: List[Team]): List[Team] = {
    require(playoffTeams.length % 2 == 0,
      s"There must be an even number of teams in the playoffs. Instead there are ${playoffTeams.length} Teams"
    )
    def inner(playoffTeams: List[Team], accumulator:List[Team]): List[Team] = {
      playoffTeams match {
//       Match if there are at least two elements in the list.
        case first :: second :: rest =>
          val winner = simulatePlayoffMatch(first, second)
          return inner(rest, winner :: accumulator)
//         Match any other list (including empty and one-element lists).
        case _ =>
          return accumulator
      }
    }
    inner(playoffTeams, List())
  }

  /**
   * Simulate knockout tournament.
   *
   * simKnockoutRound() simulates a single round of the tournament.
   * The result of this function is then a smaller list of the winners from their games.
   * These players are then randomly faced off against each other and the process continues until one person remains.
   * The winner is the last team standing.
   *
   * @param playoffTeams List of teams still alive in the playoffs.
   * @return The team that won the playoffs.
   */
  def simulatePlayoffs(playoffTeams: List[Team]): Team = {
//    Base Case: The final team remaining.
    if (playoffTeams.length == 1) return playoffTeams.head

//    Recursive Logic
    val shuffledTeams = Random.shuffle(playoffTeams)

//    Get the teams that won the first round of knockouts.
    val winningTeams = simKnockoutRound(playoffTeams)
    simulatePlayoffs(winningTeams)
  }
}
