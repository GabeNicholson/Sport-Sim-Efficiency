/**
 * Creates teams and plays them against each other.
 */

import scala.util.Random
import scala.collection.mutable.ListBuffer

object Main extends App {
  /** Creates `numTeams` teams each with a random skill level.
   *
   * @param numTeams The number of teams that will be playing against each other.
   * @return a list of all the teams generated.
   */
  private def initializeTeams(numTeams: Int): List[Team] = {
    val allTeams = for (num <- 1 to numTeams) yield {
      new Team(s"team$num", Random.nextInt(99) + 1)
    }
    allTeams.toList
  }
  val numIterations = 1
  val numberOfPlayoffTeams = 8
  val numTeams = 30
  var soccerChampions = 0
  var hockeyChampions = 0
  val numSimulations = 10000
  for (i <- 1 to numSimulations) {
    val allTeams = initializeTeams(numTeams)
    //    Save the highest skill level out of all teams so the champion can be checked if they had the highest skill.
    val highestSkillLevel = allTeams.foldLeft(0)(
      (acc, currentTeam) => if (currentTeam.skillLevel > acc) currentTeam.skillLevel else acc
    )
    val tourney = new Tournament(allTeams)

    //    seasonScores is a List containing the Team instances who won.
    val seasonScores = tourney.simulateRegularSeason(numIterations)

    val numRegularSeasonGames = numIterations * numTeams * (numTeams - 1)

    //  Reduce the number of seasonGames so that the total number of games played are the same for both sports.
    val reducedSeasonScores = seasonScores.take(numRegularSeasonGames - (numberOfPlayoffTeams - 1))

    //    println(s"${reducedSeasonScores.length} Number of reduced season games")
    val playoffTeams = tourney.getTopNTeams(reducedSeasonScores, numberOfPlayoffTeams)

    //    The number of games played in the playoffs is equal to N-1 where N is numberOfPlayoffTeams.
    val hockeyWinner: Int = tourney.simulatePlayoffs(playoffTeams).skillLevel
    if (hockeyWinner == highestSkillLevel) hockeyChampions += 1

    //  Get the team with the most wins during the regular season.
    val soccerWinner: Int = tourney.getTopNTeams(seasonScores, 1).head.skillLevel
    if (soccerWinner == highestSkillLevel) soccerChampions += 1
  }

  println(soccerChampions.toDouble / numSimulations)
  println(hockeyChampions.toDouble / numSimulations)
}