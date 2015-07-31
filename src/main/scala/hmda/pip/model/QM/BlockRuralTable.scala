package hmda.pip.model.QM

import com.vividsolutions.jts.geom.Geometry
import hmda.pip.model.{ GeospatialSearch, GeometryTable }
import hmda.pip.pg.PostgisDriver.api._

case class BlockRuralRow(gid: Long, shape_type: String, geom: Geometry)

class BlockRuralTable(tag: Tag) extends GeometryTable[BlockRuralRow](tag, "blockdissolve") {
  def shapeType = column[String]("shape_type")
  def * = (gid, shapeType, geom) <> (BlockRuralRow.tupled, BlockRuralRow.unapply)
}

object BlockRuralTable extends GeospatialSearch[BlockRuralTable, BlockRuralRow] {
  override val tableQuery = TableQuery[BlockRuralTable]
}
