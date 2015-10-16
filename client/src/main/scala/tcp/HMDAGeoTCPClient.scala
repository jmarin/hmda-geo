package tcp

import akka.actor.ActorSystem
import scala.concurrent.duration._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Tcp
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

object HMDAGeoTCPClient extends HMDAGeoTCPServiceClient {

  implicit val askTimeout: Timeout = 1000.millis
  implicit val system: ActorSystem = ActorSystem("hmda-geo-tcp-client")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  override val config = ConfigFactory.load()

  lazy val host = config.getString("hmda.geo.tcp.host")
  lazy val port = config.getInt("hmda.geo.tcp.port")

  override lazy val connection = Tcp().outgoingConnection(host, port)

  def close(): Unit = {
    system.shutdown()
  }

}
