package tcp

import akka.stream.scaladsl.Tcp
import geometry.Point
import tcp.model.CensusGeography
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.Config

trait HMDAGeoTCPServiceClient extends CensusGeography {

  implicit val askTimeout: Timeout = 1000.millis
  implicit val system: ActorSystem = ActorSystem("hmda-geo-tcp-client")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def host: String
  def port: Int

  val config: Config

  val connection = Tcp().outgoingConnection(host, port)

  def findGeoidByPoint(p: Point, geography: Geography): Unit = {
    geography match {
      case Tract =>

      case _ =>
    }
  }

}
