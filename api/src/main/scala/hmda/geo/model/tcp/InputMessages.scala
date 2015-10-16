package hmda.geo.model.tcp

import geometry.Point

object InputMessages {

  case class InputCensusGeography(geography: String, longitude: Double, latitude: Double)

}
