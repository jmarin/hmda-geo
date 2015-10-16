package tcp

import com.typesafe.config.ConfigFactory

object HMDAGeoTCPClient extends HMDAGeoTCPServiceClient {
  override val config = ConfigFactory.load()

  lazy val host = config.getString("hmda.geo.http.host")
  lazy val port = config.getInt("hmda.geo.http.port")

}
