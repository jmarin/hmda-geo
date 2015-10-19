package hmda.geo.client.tcp

import akka.stream.scaladsl.{ Flow, Sink, Source, Tcp }
import geometry.Point
import hmda.geo.shared.model.tcp.InputCensusGeography
import hmda.geo.shared.protocol.tcp.CensusTCPJsonProtocol

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.{ ByteString, Timeout }
import com.typesafe.config.Config
import spray.json._

import scala.concurrent.Future
import scala.util.{ Failure, Success }

trait HMDAGeoTCPServiceClient extends CensusTCPJsonProtocol {

  implicit val askTimeout: Timeout
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  def host: String
  def port: Int

  val config: Config

  lazy val connection = Tcp().outgoingConnection(host, port)

  def findGeoidByPoint: Flow[InputCensusGeography, String, Unit] = {
    Flow[InputCensusGeography]
      .map(_.toJson.toString)
      .map(ByteString(_))
      .via(connection)
      .map(_.utf8String)
  }

}
