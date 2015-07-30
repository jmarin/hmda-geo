package hmda.pip.model.QM

import geometry.Point
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration._
import hmda.pip.model.PipResult

class CountyRuralSpec extends FlatSpec with MustMatchers {
  "County Rural polygon" must "containt POINT(-107, 40)" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val point = Point(-107, 40, 4326)
    val fIsContained = CountyRuralTable.containsPoint(point.jtsGeometry)
    val pipResult = Await.result(fIsContained, 10.seconds)
    pipResult mustBe PipResult(true)
  }
}
