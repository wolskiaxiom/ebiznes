package models

import play.api.libs.json.Json

case class Parrot (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Parrot {
  implicit val parrotFormat = Json.format[Parrot]
}