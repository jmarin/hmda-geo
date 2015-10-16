package hmda.geo.shared.protocol.tcp

import hmda.geo.shared.model.tcp.InputMessages._
import spray.json.DefaultJsonProtocol

trait CensusTCPJsonProtocol extends DefaultJsonProtocol {
  implicit val inputMessagesFormat = jsonFormat3(InputCensusGeography.apply)
}
