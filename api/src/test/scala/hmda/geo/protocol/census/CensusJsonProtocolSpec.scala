package hmda.geo.protocol.census

import hmda.geo.shared.model.tcp.InputMessages
import InputMessages.InputCensusGeography
import hmda.geo.shared.model.census.TractResult
import hmda.geo.shared.protocol.census.CensusJsonProtocol
import hmda.geo.shared.protocol.tcp.CensusTCPJsonProtocol
import org.scalatest._
import spray.json._

class CensusJsonProtocolSpec extends FlatSpec with MustMatchers with CensusJsonProtocol with CensusTCPJsonProtocol {

  "Tracts" should "convert to and from JSON" in {
    val tract = TractResult("32", "023", "960200", "32023960200", "Census Tract 9602", "G5020", "S", -2147483648, 4386983, "+38.1235057", "-116.6775170")
    tract.toJson.convertTo[TractResult] mustBe tract
  }

  "Input Census Geographies" should "convert to and from JSON" in {
    val inputCensusGeography = InputCensusGeography("tract", -77.5, 38.0)
    inputCensusGeography.toJson.convertTo[InputCensusGeography] mustBe inputCensusGeography
  }

}
