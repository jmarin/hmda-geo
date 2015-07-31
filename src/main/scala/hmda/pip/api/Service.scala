package hmda.pip.api

import java.net.InetAddress
import java.time.Instant

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.coding.{ Deflate, Gzip, NoCoding }
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import com.typesafe.scalalogging.Logger
import geometry.Point
import hmda.pip.model.QM.{ BlockRuralTable, CountyRuralTable }
import hmda.pip.model.Status
import hmda.pip.protocol.PipJsonProtocol
import org.slf4j.LoggerFactory
import spray.json._

import scala.concurrent.ExecutionContextExecutor

trait Service extends PipJsonProtocol {
  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: ActorMaterializer

  def config: Config

  val logger: LoggingAdapter

  lazy val log = Logger(LoggerFactory.getLogger("hmda-pip"))

  val routes = {
    path("status") {
      get {
        encodeResponseWith(NoCoding, Gzip, Deflate) {
          complete {
            val now = Instant.now.toString
            val host = InetAddress.getLocalHost.getHostName
            val status = Status("OK", "hmda-pip", now, host)
            log.debug(status.toJson.toString())
            ToResponseMarshallable(status)
          }
        }
      }
    } ~
      path("pip" / Segment) { table =>
        parameters('latitude.as[Double], 'longitude.as[Double]) { (lat, lon) =>
          encodeResponseWith(NoCoding, Gzip, Deflate) {
            complete {
              table match {
                case "county_rural" =>
                  val p = Point(lon, lat, 4269)
                  CountyRuralTable.containsPoint(p.jtsGeometry)
                case "blockdissolve" =>
                  val p = Point(lon, lat, 4269)
                  BlockRuralTable.containsPoint(p.jtsGeometry)
                case _ => BadRequest
              }
            }
          }
        }
      }

  }

}
