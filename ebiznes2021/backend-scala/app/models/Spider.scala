package models

import play.api.libs.json.Json

case class Spider (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)

object Spider {
  implicit val spiderFormat = Json.format[Spider]
}