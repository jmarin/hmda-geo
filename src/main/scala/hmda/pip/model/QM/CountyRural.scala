package hmda.pip.model.QM

import com.vividsolutions.jts.geom.Geometry
import hmda.pip.model.{ GeospatialSearch, GeometryTable }
import hmda.pip.pg.PostgisDriver.api._

case class CountyRuralRow(gid: Long, name: String, geom: Geometry)

class CountyRuralTable(tag: Tag) extends GeometryTable[CountyRuralRow](tag, "county_rural") {
  def name = column[String]("name")
  def * = (gid, name, geom) <> (CountyRuralRow.tupled, CountyRuralRow.unapply)
}

object CountyRuralTable extends GeospatialSearch[CountyRuralTable, CountyRuralRow] {
  override val tableQuery = TableQuery[CountyRuralTable]
}
