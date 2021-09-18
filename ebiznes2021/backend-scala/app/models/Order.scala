package models

import play.api.libs.json.Json

case class Animal (id: Long, name: String, animalType: String, age:Int, price: BigDecimal)
case class Order (id: Long,
                  customerEmail: String,
                  customerNick: String,
                  customerAddress1: String,
                  customerAddress2: String,
                  customerCity: String,
                  customerZipcode: String,
                  totalPrice:Int,
                  comments: String
                 )

object Order {
  implicit val orderFormat = Json.format[Order]
}