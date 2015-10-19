package hmda.geo.client.tcp

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Source, Sink}
import akka.util.Timeout
import hmda.geo.shared.protocol.tcp.CensusTCPJsonProtocol
import scala.concurrent.duration._
import geometry.Point
import hmda.geo.shared.model.tcp.InputCensusGeography
import org.scalatest.{MustMatchers, FlatSpec}
import spray.json._

import scala.util.{Failure, Success}

class HMDAGeoTCPClientSpec extends FlatSpec with MustMatchers with CensusTCPJsonProtocol {

  implicit val askTimeout: Timeout = 1000.millis
  implicit val system: ActorSystem = ActorSystem("hmda-geo-tcp-client-spec")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val ec = system.dispatcher

  "Searching for tracts2 by point" must "return tract id" in {
    val p = Point(-93.2, 33.8, 4326)
    val inputMessage =  InputCensusGeography("tract", -93.2, 33.8)
    val source = Source(() => List(inputMessage).iterator)
    source.via(HMDAGeoTCPClient.findGeoidByPoint).runWith(Sink.head).onComplete {
      case Success(s) => s mustBe "05099090100"
      case Failure(_) => fail("Failure")
    }
  }
}
