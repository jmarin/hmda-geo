package hmda.geo.model.census

object CensusGeography {
  sealed trait Geography
  case object State extends Geography
  case object County extends Geography
  case object Tract extends Geography
  case object Block extends Geography
  val all = Seq(State, County, Tract, Block)
}
