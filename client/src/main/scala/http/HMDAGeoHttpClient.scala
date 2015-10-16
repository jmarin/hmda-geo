package hmda.geo.client.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.typesafe.config.ConfigFactory
import hmda.geo.client.api.model.census.HMDAGeoTractResult
import hmda.geo.client.model.ResponseError
import hmda.geo.client.api.protocol.census.HMDAGeoCensusJsonProtocol
import hmda.geo.shared.model.Status
import hmda.geo.shared.protocol.StatusJsonProtocol
import http.HMDAGeoHttpServiceClient
import scala.concurrent.{ ExecutionContext, Future }
import geometry.Point

object HMDAGeoHttpClient extends HMDAGeoHttpServiceClient with StatusJsonProtocol with HMDAGeoCensusJsonProtocol {
  override val config = ConfigFactory.load()

  lazy val host = config.getString("hmda.geo.http.host")
  lazy val port = config.getString("hmda.geo.http.port")

  def status: Future[Either[ResponseError, Status]] = {
    implicit val ec: ExecutionContext = system.dispatcher
    sendGetRequest(Uri("/status")).flatMap { response =>
      response.status match {
        case OK => Unmarshal(response.entity).to[Status].map(Right(_))
        case _ => sendResponseError(response)
      }
    }
  }

  def findTractByPoint(p: Point): Future[Either[ResponseError, HMDAGeoTractResult]] = {
    implicit val ec: ExecutionContext = system.dispatcher
    val lat = p.y
    val lon = p.x
    sendGetRequest(Uri(s"/tracts?latitude=${lat}&longitude=${lon}")).flatMap { response =>
      response.status match {
        case OK => Unmarshal(response.entity).to[HMDAGeoTractResult].map(Right(_))
        case _ => sendResponseError(response)
      }
    }
  }

}