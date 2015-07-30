package hmda.pip.model

import com.vividsolutions.jts.geom.Geometry
import hmda.pip.pg.PostgisDriver.api._

abstract class GeometryTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
  val gid = column[Long]("gid", O.AutoInc, O.PrimaryKey)
  val geom = column[Geometry]("geom")
}
