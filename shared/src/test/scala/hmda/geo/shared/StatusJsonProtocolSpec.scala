package hmda.geo.shared

import java.net.InetAddress
import java.util.Calendar
import org.scalatest._
import hmda.geo.shared.model.Status
import hmda.geo.shared.protocol.StatusJsonProtocol
import spray.json._

class StatusJsonProtocolSpec extends FlatSpec with MustMatchers with StatusJsonProtocol {

  "Status" should "convert to and from JSON" in {
    val date = Calendar.getInstance().getTime().toString
    val status = Status("OK", "hmda-pip", date, InetAddress.getLocalHost.getHostName)
    status.toJson.convertTo[Status] mustBe status
  }

}
