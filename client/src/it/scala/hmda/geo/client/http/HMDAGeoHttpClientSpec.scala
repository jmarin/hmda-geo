package hmda.geo.client.http

import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration._
import geometry.Point

class HMDAGeoHttpClientSpec extends FlatSpec with MustMatchers {
  
  "A request to /status" must "return a status object" in {
    val maybeStatus = Await.result(HMDAGeoHttpClient.status, 2.seconds)
    maybeStatus match {
      case Right(s) =>
        s.status mustBe "OK"
      case Left(b) =>
        b.desc mustBe "503 Service Unavailable"
        fail("SERVICE_UNAVAILABLE")
    }
  }

  "A requet to /tracts" must "find the correct tract by latitude,longitude" in {
    val p = Point(-93.2, 33.8)
    val maybeTract = Await.result(HMDAGeoHttpClient.findTractByPoint(p), 2.seconds)
    maybeTract match {
      case Right(t) =>
        t.intptlon mustBe "-093.2684491"
        t.awater mustBe 3132857
        t.mtfcc mustBe "G5020"
        t.aland mustBe 726760770
        t.namelsad mustBe "Census Tract 901"
        t.tractce mustBe "090100"
        t.statefp mustBe "05"
        t.intptlat mustBe "+33.6857159"
        t.funcstat mustBe "S"
        t.countyfp mustBe "099"
        t.geoid mustBe "05099090100"
      case Left(b) =>
        b.desc mustBe "503 Service Unavailable"
        fail("SERVICE_UNAVAILABLE")
    }
  }
}

