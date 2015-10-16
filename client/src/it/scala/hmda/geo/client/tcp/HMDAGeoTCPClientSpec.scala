package hmda.geo.client.tcp

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.util.Timeout
import scala.concurrent.duration._
import geometry.Point
import hmda.geo.shared.model.tcp.InputCensusGeography
import org.scalatest.{MustMatchers, FlatSpec}
import tcp.HMDAGeoTCPClient

import scala.util.{Failure, Success}

class HMDAGeoTCPClientSpec extends FlatSpec with MustMatchers {

  implicit val askTimeout: Timeout = 1000.millis
  implicit val system: ActorSystem = ActorSystem("hmda-geo-tcp-client-spec")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val ec = system.dispatcher

  "Searching for tracts by point" must "return tract id" in {
    val p = Point(-93.2, 33.8, 4326)
    val inputMessage =  InputCensusGeography("tract", -93.2, 33.8)
    val f = HMDAGeoTCPClient.findGeoidByPoint(inputMessage).grouped(1).runWith(Sink.head)
    f.onComplete {
      case Success(geoid) => geoid(0) mustBe "05099090100"
      case Failure(_) => fail("Could not retrieve correct census tract id")
    }
  }
}
