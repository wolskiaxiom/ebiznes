package models

import play.api.libs.json.Json

case class Snake (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Snake {
  implicit val snakeFormat = Json.format[Snake]
}