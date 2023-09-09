import org.scalatest.funsuite.AnyFunSuite

class TeamTest extends AnyFunSuite {
  test("Team should be created with valid name and skill level") {
    val team = new Team("Team1", 50)
    assert(team.name == "Team1")
    assert(team.skillLevel == 50)
  }

  test("Team should not be created with negative skill level") {
    assertThrows[IllegalArgumentException] {
      new Team("Team1", -50)
    }
  }
}