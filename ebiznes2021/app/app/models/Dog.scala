package models

import play.api.libs.json.Json

case class Dog (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Dog {
  implicit val dogFormat = Json.format[Dog]
}