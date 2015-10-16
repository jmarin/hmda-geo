package hmda.geo.shared.model.tcp

object InputMessages {

  case class InputCensusGeography(geography: String, longitude: Double, latitude: Double)

}
