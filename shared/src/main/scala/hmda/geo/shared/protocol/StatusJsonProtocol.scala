package hmda.geo.shared.protocol

import hmda.geo.shared.model.Status
import spray.json.DefaultJsonProtocol

trait StatusJsonProtocol extends DefaultJsonProtocol {
  implicit val statusFormat = jsonFormat4(Status.apply)
}
