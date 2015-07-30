package hmda.pip.model

import geometry.Point
import hmda.pip.pg.PostgisDriver
import hmda.pip.pg.PostgisDriver.api._
import slick.lifted.TableQuery

import scala.concurrent.{ ExecutionContext, Future }

trait GeospatialSearch[T <: GeometryTable[A], A] {
  val tableQuery: TableQuery[T]

  def containsPoint(p: Point)(implicit ec: ExecutionContext): Future[PipResult] = {
    val slickProfile = PostgisDriver
    val db = slickProfile.api.Database.forConfig("pipdb")
    val q = tableQuery.filter(c => c.geom.contains(p.jtsGeometry)).map(c => c.gid)
    db.run(q.result).map(r => if (r.size > 0) PipResult(true) else PipResult(false))
  }
}
