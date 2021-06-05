package models

import play.api.libs.json.Json

case class Pig (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Pig {
  implicit val pigFormat = Json.format[Pig]
}