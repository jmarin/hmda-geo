package hmda.pip.model.QM

import geometry.Point
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration._
import hmda.pip.model.PipResult

class CountyRuralSpec extends FlatSpec with MustMatchers {
  "County Rural polygon" must "containt POINT(-107, 40)" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val point = Point(-107, 40, 4269)
    val fIsContained = CountyRuralTable.containsPoint(point.jtsGeometry)
    val pipResult = Await.result(fIsContained, 10.seconds)
    pipResult mustBe PipResult(true)
  }

  "County Rural polygon" must "not contain POINT(0,0)" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val point = Point(0, 0, 4269)
    val fIsContained = CountyRuralTable.containsPoint(point.jtsGeometry)
    val pipResult = Await.result(fIsContained, 10.seconds)
    pipResult mustBe PipResult(false)
  }

  "Block Rural polygon" must "containt POINT(-107, 40)" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val point = Point(-107, 40, 4269)
    val fIsContained = BlockRuralTable.containsPoint(point.jtsGeometry)
    val pipResult = Await.result(fIsContained, 10.seconds)
    pipResult mustBe PipResult(true)
  }

  "Block Rural polygon" must "not containt POINT(0, 0)" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val point = Point(0, 0, 4269)
    val fIsContained = BlockRuralTable.containsPoint(point.jtsGeometry)
    val pipResult = Await.result(fIsContained, 10.seconds)
    pipResult mustBe PipResult(false)
  }
}
