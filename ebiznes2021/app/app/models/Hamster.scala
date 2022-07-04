package models

import play.api.libs.json.Json

case class Hamster (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Hamster {
  implicit val hamsterFormat = Json.format[Hamster]
}