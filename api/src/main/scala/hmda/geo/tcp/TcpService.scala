package hmda.geo.tcp

import akka.stream.Materializer
import akka.stream.io.Framing
import akka.stream.scaladsl._
import akka.util.ByteString
import com.typesafe.scalalogging.Logger
import geometry.Point
import hmda.geo.model.tcp.InputMessages.InputCensusGeography
import hmda.geo.protocol.census.CensusJsonProtocol
import hmda.geo.service.TractService
import org.slf4j.LoggerFactory
import spray.json._

import scala.concurrent.{ ExecutionContextExecutor, Future }
import spray.json._

trait TcpService extends CensusJsonProtocol {

  lazy val tcpLog = Logger(LoggerFactory.getLogger("hmda-geo-census-tract-tcp"))

  implicit val materializer: Materializer
  implicit def executor: ExecutionContextExecutor

  val tractHandler = Sink.foreach[Tcp.IncomingConnection] { conn =>
    tcpLog.debug("Client connected from: " + conn.remoteAddress)
    conn handleWith censusFlow
  }

  val censusFlow: Flow[ByteString, ByteString, Unit] = {
    Flow[ByteString]
      .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))
      .map(_.utf8String)
      .map(s => s.parseJson.convertTo[InputCensusGeography])
      .mapAsync(4) { c =>
        val p = Point(c.longitude, c.latitude, 4326)
        c.geography match {
          case "tract" =>
            for {
              t <- TractService.findGeoidByPoint(p)
            } yield ByteString(t)
          case "" => Future(ByteString("Bad Request"))
        }
      }
  }

}
