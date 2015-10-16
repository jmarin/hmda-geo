package hmda.geo.shared.model.census

case class TractResult(
  statefp: String,
  countyfp: String,
  tractce: String,
  geoid: String,
  namelsad: String,
  mtfcc: String,
  funcstat: String,
  aland: Double,
  awater: Double,
  intptlat: String,
  intptlon: String
)

object TractResult {
  def empty: TractResult = {
    TractResult("", "", "", "", "", "", "", 0.0, 0.0, "", "")
  }
}

