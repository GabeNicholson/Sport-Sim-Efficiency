/**
 * Creates teams and plays them against each other.
 */

import scala.util.Random
import scala.collection.mutable.ListBuffer

object Main extends App {
  private def initializeTeams(): List[Team] = {
    val gabe = new Team("Gabe", 100)
    val shana = new Team("Shana", 50)
    val andrew = new Team("Andrew", 50)
    val dim = new Team("Dim", 75)
    val dim2 = new Team("Dim2", 60)
    val dim3 = new Team("Dim3", 55)
    //      Shuffle the List so that the for loop truncation during simulateRegularSeason is not biased.
    Random.shuffle(List(shana, andrew, dim, dim2, dim3, gabe))
  }

  val soccerChampions = ListBuffer[String]()
  val hockeyChampions = ListBuffer[String]()

  for (i <- 1 to 100000) {
    val allTeams = initializeTeams()
    val tourney = new Tournament(allTeams)
    val numIterations = 5
    val numberOfPlayoffTeams = 4

    //    seasonScores is a List containing the Team instances who won.
    val seasonScores = tourney.simulateRegularSeason(numIterations)
    val numTeams = allTeams.length
    //    println(s"${(numIterations * numTeams * (numTeams - 1))}Regular season games")
    
    //  Reduce the number of seasonGames so that the total number of games played are the same for both sports.
    val reducedSeasonScores = seasonScores.take((numIterations * numTeams * (numTeams - 1)) - (numberOfPlayoffTeams - 1))
    //    println(s"${reducedSeasonScores.length} Number of reduced season games")
    val playoffTeams = tourney.getTopNTeams(reducedSeasonScores, numberOfPlayoffTeams)
    //    println(s"${numberOfPlayoffTeams - 1} Number of playoff games")

    //    The number of games played in the playoffs is equal to N-1 where N is numberOfPlayoffTeams.
    val hockeyWinner: String = tourney.simulatePlayoffs(playoffTeams).name
    hockeyChampions += hockeyWinner

    //  Get the team with the most wins during the regular season.
    val soccerWinner: String = tourney.getTopNTeams(seasonScores, 1).head.name
    soccerChampions += soccerWinner
  }

  println(soccerChampions.count(_ == "Gabe"))
  println(hockeyChampions.count(_ == "Gabe"))
}