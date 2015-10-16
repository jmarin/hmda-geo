package hmda.geo.shared.protocol.census

import hmda.geo.shared.model.census.TractResult
import spray.json.DefaultJsonProtocol

trait CensusJsonProtocol extends DefaultJsonProtocol {
  implicit val tractResultFormat = jsonFormat11(TractResult.apply)
}

