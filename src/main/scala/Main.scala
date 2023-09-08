  /**
   * Creates teams and plays them against each other.
   */
object Main extends App {

    private def initializeTeams(): List[Team] = {
      val gabe = new Team("Gabe", 85)
      val shana = new Team("Shana", 50)
      val andrew = new Team("Andrew", 50)
      val dim = new Team("Dim", 75)
      val dim2 = new Team("Dim2", 60)
      val dim3 = new Team("Dim3", 55)
      List(gabe, shana, andrew, dim, dim2, dim3)
    }

    val allTeams = initializeTeams()
    val tourney = new Tournament(allTeams)

    val numberOfGames = 1
    val numberOfPlayoffTeams = 4

    //    seasonScores is a List containing the Team instances who won.
    val seasonScores = tourney.simulateRegularSeason(numberOfGames)
    val playoffTeams = tourney.getTopNTeams(seasonScores, numberOfPlayoffTeams)
    val winner = tourney.simulatePlayoffs(playoffTeams).name
    println(s"$winner won the tournament!")
}