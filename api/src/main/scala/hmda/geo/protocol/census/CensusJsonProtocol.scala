package hmda.geo.protocol.census

import hmda.geo.model.census.TractResult
import spray.json.DefaultJsonProtocol
import hmda.geo.model.tcp.InputMessages._

trait CensusJsonProtocol extends DefaultJsonProtocol {
  implicit val tractResultFormat = jsonFormat11(TractResult.apply)
  implicit val censusGeographyFormat = jsonFormat3(InputCensusGeography.apply)
}

