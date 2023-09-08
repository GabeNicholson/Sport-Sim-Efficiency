object Main extends App {
  /**
   * Creates teams and plays them against each other.
   */
  val Gabe = new Team("Gabe", 85)
  val Shana = new Team("Shana", 50)
  val Andrew = new Team("Andrew", 50)
  val Dim = new Team("Dim", 75)
  val Dim2 = new Team("Dim2", 60)
  val Dim3 = new Team("Dim3", 55)
  val allTeams = List(Gabe, Shana, Andrew, Dim, Dim2, Dim3)

  val tourney = new Tournament(allTeams)
  //    seasonScores is a List containing the Team instances who won.
  val seasonScores = tourney.simulateRegularSeason(1)
  val playoffTeams = tourney.getTopNTeams(seasonScores, 2)
  val winner = tourney.simulatePlayoffMatch(playoffTeams).name
  println(s"$winner won the tournament!")
}