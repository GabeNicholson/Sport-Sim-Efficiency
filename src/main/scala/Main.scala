/**
 * Creates teams and plays them against each other.
 */

import scala.util.Random

object Main extends App {
  private def initializeTeams(): List[Team] = {
    val gabe = new Team("Gabe", 85)
    val shana = new Team("Shana", 50)
    val andrew = new Team("Andrew", 50)
    val dim = new Team("Dim", 75)
    val dim2 = new Team("Dim2", 60)
    val dim3 = new Team("Dim3", 55)
    //      Shuffle the List so that the for loop truncation during simulateRegularSeason is not biased.
    Random.shuffle(List(gabe, shana, andrew, dim, dim2, dim3))
  }

  val allTeams = initializeTeams()
  val tourney = new Tournament(allTeams)
  private val numIterations = 1
  private val numberOfPlayoffTeams = 4

  //    seasonScores is a List containing the Team instances who won.
  val seasonScores = tourney.simulateRegularSeason(numIterations)
  private val numTeams = allTeams.length
  //  Reduce the number of seasonGames so that the total number of games played are the same for both sports.
  private val reducedSeasonGames = seasonScores.take((numIterations * numTeams * (numTeams - 1)) - numTeams)
  val playoffTeams = tourney.getTopNTeams(reducedSeasonGames, numberOfPlayoffTeams)

  //    The number of games played in the playoffs is equal to N-1 where N is numberOfPlayoffTeams.
  val hockeyWinner: String = tourney.simulatePlayoffs(playoffTeams).name
  println(s"$hockeyWinner won the Hockey League!")

  //  Get the team with the most wins during the regular season.
  val soccerWinner: String = tourney.getTopNTeams(reducedSeasonGames, 1).head.name
  println(s"$soccerWinner won the Premier League!")
}