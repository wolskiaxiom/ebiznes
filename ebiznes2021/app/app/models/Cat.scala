package models

import play.api.libs.json.Json

case class Cat (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Cat {
  implicit val catFormat = Json.format[Cat]
}